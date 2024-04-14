package cn.zpeace.bootstrap.sql;


import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.zpeace.bootstrap.anno.DataScope;
import cn.zpeace.bootstrap.util.JsonUtils;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.ResultHandler;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * select sql 拦截器
 * 拦截DataScope标注的方法，进行数据过滤
 * 原理：在原sql拼接where语句
 * @author zpeace
 */
@Slf4j
@Component
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class DataScopeInterceptor implements Interceptor {


    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 因为拦截的是StatementHandler，所以可以强转为StatementHandler
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        MetaObject metaObject = SystemMetaObject.forObject(statementHandler);
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
        if (this.shouldFilter(mappedStatement)) {
            BoundSql boundSql = statementHandler.getBoundSql();
            String originalSql = boundSql.getSql();
            log.info("originalSql: {}", originalSql);
            String newSql = originalSql + " AND dept_id = 1";
            metaObject.setValue("delegate.boundSql.sql", newSql);

        }

        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        }
        return target;
    }

    /**
     * mapper类的那些方法需要数据过滤
     * @return ture -> 需要过滤 false -> 不需要过滤
     */
    private Boolean shouldFilter(MappedStatement mappedStatement) {
        try {
            String id = mappedStatement.getId();
            String className = id.substring(0, id.lastIndexOf("."));
            String methodName = id.substring(id.lastIndexOf(".") + 1);
            Class<?> aClass = Class.forName(className);
            Method method = ReflectUtil.getMethodByName(aClass, methodName);
            if (method == null) {
                return false;
            }
            return method.isAnnotationPresent(DataScope.class);
        } catch (Exception e) {
            log.error("DataFilter get annotation failed: {}", e.getMessage(), e);
            return false;
        }
    }

    @Override
    public void setProperties(Properties properties) {
    }




    /**
     * 拼装sql
     * @param originalSql 旧sql
     * @param dataScope mapper类上的dataScope注解
     * @return 新的sql语句
     */
    private String concatSql(String originalSql,DataScope dataScope) {
        String concatSql = "";
        // 将数据过滤sql添加进原sql中
        String where = "where";
        String group = "group by";
        String order = "order by";
        int whereIndex = originalSql.toLowerCase().indexOf(where);
        int orderIndex = originalSql.toLowerCase().indexOf(order);
        int groupIndex = originalSql.toLowerCase().indexOf(group);

        if (whereIndex > - 1) {
            String subSql = originalSql.substring(0, whereIndex + where.length() + 1);
            subSql = subSql + " " + concatSql + " AND " + originalSql.substring(whereIndex + where.length() + 1);
            return subSql;
        }

        if (groupIndex > - 1) {
            String subSql = originalSql.substring(0, groupIndex);
            subSql = subSql + " WHERE " + concatSql + " " + originalSql.substring(groupIndex);
            return subSql;
        }

        if (orderIndex > - 1) {
            String subSql = originalSql.substring(0, orderIndex);
            subSql = subSql + " WHERE " + concatSql + " " + originalSql.substring(orderIndex);
            return subSql;
        }

        originalSql += " WHERE " + concatSql;
        return originalSql;
    }
}

package cn.zpeace.bootstrap.util;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.Map;


/**
 * Created on 2021-12-27.
 *
 * @author skiya
 */
@Slf4j
@Component
public class SpringUtils implements ApplicationContextAware, BeanFactoryPostProcessor {

    /**
     * 上下文对象实例
     */
    private static ApplicationContext applicationContext;

    private static ConfigurableListableBeanFactory beanFactory;

    @Override
    public void setApplicationContext(@Nonnull ApplicationContext applicationContext) throws BeansException {
        SpringUtils.applicationContext = applicationContext;
        log.info("Inject applicationContext succeeded");
    }

    @Override
    public void postProcessBeanFactory(@NotNull ConfigurableListableBeanFactory beanFactory) throws BeansException {
        SpringUtils.beanFactory = beanFactory;
        log.info("Inject beanFactory succeeded");
    }

    /**
     * 获取applicationContext
     */
    public static ApplicationContext getApplicationContext() {
        if (applicationContext == null) {
            throw new IllegalArgumentException("ApplicationContext wasn't injected");
        }
        return applicationContext;
    }

    public static ListableBeanFactory getBeanFactory() {
        return null == beanFactory ? applicationContext : beanFactory;
    }

    /**
     * 通过beanName获取Bean,并且自动强转
     *
     * @param name 名字
     * @return bean
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        return (T) getBeanFactory().getBean(name);
    }

    /**
     * 通过class获取Bean.
     *
     * @param clazz 类型
     * @return bean
     */
    public static <T> T getBean(Class<T> clazz) {
        return getBeanFactory().getBean(clazz);
    }

    /**
     * 得到某个类的所有 beans
     *
     * @param type 类型
     * @return 类的所有 beans
     */
    public static <T> Map<String, T> getBeansOfType(Class<T> type) {
        return getBeanFactory().getBeansOfType(type);
    }
}

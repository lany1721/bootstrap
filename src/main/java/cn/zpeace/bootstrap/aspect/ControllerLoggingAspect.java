package cn.zpeace.bootstrap.aspect;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * 打印controller类的入参、出参、执行时间等
 *
 * @author skiya
 * @date Created on 2021-9-24.
 */
@Aspect
@Component
public class ControllerLoggingAspect {

    /**
     * 切入点：所有controller
     */
    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
    public void restController() {
    }

    private Logger logger(JoinPoint joinPoint) {
        return LoggerFactory.getLogger(joinPoint.getSignature().getDeclaringTypeName());
    }

    @Around("restController()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        Logger log = logger(joinPoint);

        // 非 debug 直接返回
        if (!log.isDebugEnabled()) {
            return joinPoint.proceed();
        }
        //  获取 全限定方法名、参数名、参数
        Signature signature = joinPoint.getSignature();
        String methodName = signature.getName();
        MethodSignature methodSignature = (MethodSignature) signature;
        ArrayList<String> argNames = CollUtil.newArrayList(methodSignature.getParameterNames());
        ArrayList<Object> args = CollUtil.newArrayList(joinPoint.getArgs());
        log.debug("method:{}, args:{}", methodName, CollUtil.zip(argNames, args));
        // 方法执行计时
        TimeInterval timer = DateUtil.timer();
        timer.start();
        // 目标方法执行 获取返回值
        Object retVal = joinPoint.proceed();
        log.debug("method:{}, duration:{}ms, return:{}", methodName, timer.interval(), retVal);
        return retVal;
    }
}

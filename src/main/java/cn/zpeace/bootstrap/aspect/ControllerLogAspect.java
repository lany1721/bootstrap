package cn.zpeace.bootstrap.aspect;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * 打印controller类的入参、出参、执行时间等
 *
 * @author skiya
 * @date Created on 2021-9-24.
 */
@Slf4j
@Aspect
@Component
public class ControllerLogAspect {

    /**
     * 切入点：所有controller
     */
    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
    public void restController() {
    }

    @Around("restController()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        //  获取 全限定方法名、参数名、参数
        Signature signature = joinPoint.getSignature();
        String proxyMethod = signature.getDeclaringTypeName() + "." + signature.getName();
        MethodSignature methodSignature = (MethodSignature) signature;
        ArrayList<String> argNames = CollUtil.newArrayList(methodSignature.getParameterNames());
        ArrayList<Object> args = CollUtil.newArrayList(joinPoint.getArgs());
        log.info("方法:{}, 入参:{}", proxyMethod, CollUtil.zip(argNames, args));
        // 方法执行计时
        TimeInterval timer = DateUtil.timer();
        timer.start();
        // 目标方法执行 获取返回值
        Object retVal = joinPoint.proceed();
        log.info("方法:{}, 耗时:{}ms, 返回值:{}", proxyMethod, timer.interval(), retVal);
        return retVal;
    }
}

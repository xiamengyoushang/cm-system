package com.excelsecu.cmsystem.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class ControllerLogAspect {

    /**
     * 切入注解的方式：切入到标记@IgnoreLog方法
     * 切入点： 所有Controller类的public方法，忽略@LogIgnore注解的方法
     */
    // 不标记注解@IgnoreLog-执行切入
    @Pointcut("execution(public * com.excelsecu.cmsystem..*Controller.*(..))&&!@annotation(com.excelsecu.cmsystem.annotation.IgnoreLog)")
    // 标记注解@IgnoreLog-执行切入
    // @Pointcut("execution(public * com.excelsecu.cmsystem..*Controller.*(..))&&@annotation(com.excelsecu.cmsystem.annotation.IgnoreLog)")
    public void controllerLogPointcut() {
    }

    @Around("controllerLogPointcut()")
    public Object aroundMethod(ProceedingJoinPoint point) throws Throwable {
        long time = System.currentTimeMillis();
        Signature signature = point.getSignature();
        try {
            log.info(">>> Begin execute {}, args: {}", signature, point.getArgs());
            Object object = point.proceed(); // 该行代表执行原方法；通过调整位置，可以调整原方法的执行顺序
            log.info("<<< End execute {} in {} ms, return: {}", signature, System.currentTimeMillis() - time, object);
            return object;
        } catch (Throwable e) {
            log.info(">>> Execute {} has occurred exception: {}", signature, e.toString());
            throw e;
        }
    }

}

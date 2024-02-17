package org.example.trading_demo.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.aspectj.lang.annotation.Aspect;

@Aspect
@Component
@Slf4j
public class LogExecutionTimeAspect {
    private static final long MIN_WANTED_TIME = 300;

    @Pointcut("@annotation(org.example.trading_demo.aspect.LogExecutionTime)")
    private void annotateMethodOnly() {}

    @Pointcut("@within(org.example.trading_demo.aspect.LogExecutionTime)")
    private void annotateClassPublicMethods() {}

    @Around("annotateMethodOnly() || annotateClassPublicMethods()")
    public Object logExecutionTimeAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - startTime;
        if (executionTime >= MIN_WANTED_TIME) {
            log.info("Execution time of method {} took {} ms", joinPoint.getSignature(), executionTime);
        }
        return result;
    }
}

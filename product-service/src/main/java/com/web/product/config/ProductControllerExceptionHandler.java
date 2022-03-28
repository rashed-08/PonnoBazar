package com.web.product.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ProductControllerExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(public * com.web.product.controller..*(..))")
    public void controllerPointCut() {

    }

    @Around("controllerPointCut()")
    public Object productExceptionHandler(ProceedingJoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();

        Object result;

        try {
            result = joinPoint.proceed();
            logger.info("Got result from {}", methodName);
        } catch (Throwable e) {
            result = handleException(e);
        } finally {
            logger.info("Exiting from {}", methodName);
        }
        return result;
    }

    private Object handleException(Throwable e) {
        if (e instanceof RuntimeException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } else {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}

package me.kruase.mipt.api.controllers.aspects;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Aspect
@Component
@Slf4j
public class ControllerAspect {
    @Getter
    private static int eventCount = 0;

    @Around("execution(* me.kruase.mipt.api.controllers.*.*(..))")
    public Object incrementAround(ProceedingJoinPoint joinPoint) throws Throwable {
        eventCount++;

        Object result = joinPoint.proceed();

        eventCount++;

        return result;
    }

    @Before("execution(* me.kruase.mipt.api.controllers.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        log.info("Controller method called: {}", joinPoint.getSignature().getName());
    }

    @Around("execution(* me.kruase.mipt.api.controllers.*.*(..))")
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        Instant start = Instant.now();

        Object result = joinPoint.proceed();

        Instant end = Instant.now();

        log.info("Duration: {} ns", (end.getNano() - start.getNano()));

        return result;
    }
}

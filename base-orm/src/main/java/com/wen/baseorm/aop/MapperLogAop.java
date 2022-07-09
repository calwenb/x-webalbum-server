package com.wen.baseorm.aop;

import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Slf4j
@Aspect
@Component
public class MapperLogAop {

    @Around("execution(public * com.wen.baseorm.mapper.impl.BaseMapperImpl.*(..))")
    public Object printfLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("====================");
        Object rs = joinPoint.proceed();
        Field pstLog = joinPoint.getTarget().getClass().getDeclaredField("pstLog");
        pstLog.setAccessible(true);
        String outLog = String.valueOf(pstLog.get(joinPoint.getTarget()));
        log.info(outLog);

        return rs;
    }

}

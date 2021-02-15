package com.aiden.cms.config;

import com.codingapi.txlcn.tc.aspect.weave.DTXResourceWeaver;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;

/**
 * @author Aiden
 * @version 1.0
 * @description 对分布式事务进行拦截，否则不会走分布式事务
 * @date 2021-2-12 22:52:35
 */

@Component
@Aspect
public class TomcatDataSourceAspect {
    @Autowired
    private DTXResourceWeaver dtxResourceWeaver;

    @Around("execution(public java.sql.Connection *.getConnection(..))")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        return dtxResourceWeaver.getConnection(() -> (Connection) point.proceed());
    }
}

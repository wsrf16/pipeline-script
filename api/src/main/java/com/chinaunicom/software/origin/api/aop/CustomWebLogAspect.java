package com.chinaunicom.software.origin.api.aop;

import com.aio.portable.swiss.hamlet.interceptor.log.HamletWebLogAspect;
import com.aio.portable.swiss.suite.log.factory.LogHubFactory;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class CustomWebLogAspect extends HamletWebLogAspect {
    public CustomWebLogAspect(LogHubFactory logHubFactory) {
        super(logHubFactory);
    }

    private final static String POINTCUT = "execution(public * com.chinaunicom.software.origin.api.controller..*.*(..)) && (@annotation(org.springframework.web.bind.annotation.GetMapping) || @annotation(org.springframework.web.bind.annotation.PostMapping) || @annotation(org.springframework.web.bind.annotation.RequestMapping))";

    @Pointcut(POINTCUT_SPECIAL)
    public void webLog() {
    }
}

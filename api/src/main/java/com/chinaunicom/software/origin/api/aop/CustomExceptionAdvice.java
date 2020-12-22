package com.chinaunicom.software.origin.api.aop;

import com.aio.portable.swiss.hamlet.interceptor.HamletExceptionAdvice;
import com.aio.portable.swiss.suite.log.factory.LogHubFactory;
import com.chinaunicom.software.origin.common.utils.AppLogHubFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Import(AppLogHubFactory.class)
@Configuration
public class CustomExceptionAdvice extends HamletExceptionAdvice {

    public CustomExceptionAdvice(LogHubFactory logHubFactory) {
        super(logHubFactory);
    }

}

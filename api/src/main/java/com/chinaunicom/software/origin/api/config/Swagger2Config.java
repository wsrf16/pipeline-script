package com.chinaunicom.software.origin.api.config;

import com.aio.portable.swiss.factories.autoconfigure.properties.Swagger2Properties;
import com.aio.portable.swiss.hamlet.swagger.SwaggerStatus;
import com.chinaunicom.software.origin.api.controller.DemoController;
import com.chinaunicom.software.origin.common.utils.BizStatusEnum;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@EnableSwagger2
@Configuration
@ConditionalOnClass(ApiInfo.class)
public class Swagger2Config {
    @Bean
    @ConditionalOnClass(ApiInfo.class)
    @ConditionalOnProperty(prefix = "swagger.api-info", name = "title")
    @ConfigurationProperties(prefix = "swagger")
    public Swagger2Properties swagger2Properties() {
        return Swagger2Properties.buildByResponse(DemoController.class.getPackage().getName(), SwaggerStatus.toResponseList(BizStatusEnum.values()));
    }

}


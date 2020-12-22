package com.chinaunicom.software.origin.api;

import com.aio.portable.swiss.suite.log.annotation.EnableLogHub;
import com.aio.portable.swiss.suite.log.impl.PropertyBean;
import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;

@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
        KafkaAutoConfiguration.class,
        RabbitAutoConfiguration.class,
}, scanBasePackages = {"com.aio.portable", "com.chinaunicom.software"})
@EnableApolloConfig
@EnableLogHub(initialBeanNames = PropertyBean.RABBITMQ_LOG_PROPERTIES)
public class ApiCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiCoreApplication.class, args);
    }
}

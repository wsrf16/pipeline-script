package com.chinaunicom.software.origin.common.utils;

import com.aio.portable.swiss.suite.log.facade.LogHub;
import com.aio.portable.swiss.suite.log.factory.LogHubFactory;
import com.aio.portable.swiss.suite.log.impl.es.rabbit.RabbitMQLog;
import com.aio.portable.swiss.suite.log.impl.slf4j.Slf4JLog;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppLogHubFactory extends LogHubFactory {
    static {
        new AppLogHubFactory();
    }

    public AppLogHubFactory() {
    }

    @Override
    public LogHub build(String className) {
        LogHub logger = LogHub.build(
//                new KafkaLog(className),
                new RabbitMQLog(className),
//                new ConsoleLog(className),
                new Slf4JLog(className))
//                .setAsync(true)
//                .setBaseLevel(LevelEnum.DEBUG)
                ;
        return logger;
    }
}
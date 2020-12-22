package com.chinaunicom.software.origin.common.config.root;

import com.chinaunicom.software.origin.common.config.root.children.BusinessConfig;
import com.chinaunicom.software.origin.common.config.root.children.JenkinsConfig;
import com.chinaunicom.software.origin.common.config.root.children.LauncherConfig;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "config")
public class ApplicationConfig {
    private LauncherConfig launcher;
    private BusinessConfig business;
    private JenkinsConfig jenkins;
}

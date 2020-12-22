package com.chinaunicom.software.origin.common.jenkins;

import com.chinaunicom.software.origin.common.config.root.ApplicationConfig;
import com.chinaunicom.software.origin.common.config.root.children.JenkinsConfig;
import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.client.JenkinsHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;

@Component
public class JenkinsServerFactory {
    private JenkinsConfig jenkinsConfig;

    public JenkinsServerFactory(ApplicationConfig applicationConfig) {
        this.jenkinsConfig = applicationConfig.getJenkins();
    }

    public JenkinsHttpClient client() {
        JenkinsHttpClient jenkinsHttpClient;
        try {
            jenkinsHttpClient = new JenkinsHttpClient(new URI(jenkinsConfig.getUrl()), jenkinsConfig.getUsername(), jenkinsConfig.getPassword());
        } catch (URISyntaxException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return jenkinsHttpClient;
    }

    public JenkinsServer server() {
        JenkinsServer jenkinsServer;
        try {
            jenkinsServer = new JenkinsServer(new URI(jenkinsConfig.getUrl()), jenkinsConfig.getUsername(), jenkinsConfig.getPassword());
        } catch (URISyntaxException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return jenkinsServer;
    }

}
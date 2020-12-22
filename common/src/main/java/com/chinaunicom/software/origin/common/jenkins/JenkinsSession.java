package com.chinaunicom.software.origin.common.jenkins;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.client.JenkinsHttpClient;
import com.offbytwo.jenkins.model.*;

import java.io.IOException;
import java.util.Map;

public class JenkinsSession {
//    pipeline {
//        agent any
//        stages {
//            stage('拉取代码') {
//                steps {
//                    checkout([$class: 'GitSCM', branches: [[name: '*/master']],
//                    doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [],
//                    userRemoteConfigs: [[credentialsId: '68f2087f-a034-4d39-a9ff-1f776dd3dfa8', url:
//                    'git@192.168.66.100:itheima_group/web_demo.git']]])
//                }
//            }
//            stage('编译构建') {
//                steps {
//                    sh label: '', script: 'mvn clean package'
//                }
//            }
//        }
//    }


    private JenkinsServer jenkinsServer;

    private JenkinsHttpClient jenkinsHttpClient;

    public void createJob(String name, String xml) {
        try {
            jenkinsServer.createJob(name, xml);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void createJobByTemplate(String name, String template, String script) {
        String xml = "";/////////////////////////////////////////////
        this.createJob(name, xml);
    }

    public JobWithDetails getJob(String name) {
        try {
            return jenkinsServer.getJob(name);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public MavenJobWithDetails getMavenJob(String name) {
        try {
            return jenkinsServer.getMavenJob(name);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public Map<String, Job> getJobs() {
        try {
            return jenkinsServer.getJobs();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public String getJobXml(String name) {
        try {
            return jenkinsServer.getJobXml(name);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public QueueReference build(String jobName) {
        try {
            return jenkinsServer.getJob(jobName).build();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    public String stopLastBuild(String jobName) {
        String response;
        try {
            Build build = jenkinsServer.getJob(jobName).getLastBuild();
            response = build.Stop();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return response;
    }

    public void deleteJob(String name) {
        try {
            jenkinsServer.deleteJob(name);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void disableJob(String name) {
        try {
            jenkinsServer.disableJob(name);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void enableJob(String name) {
        try {
            jenkinsServer.enableJob(name);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
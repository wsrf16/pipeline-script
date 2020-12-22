package com.chinaunicom.software.origin.common.jenkins.script.node.steps;

import java.text.MessageFormat;

public class MavenSteps1 {
    public final static String maven(String packageFile) {
        StringBuilder sb = new StringBuilder();
        sb.append(MessageFormat.format("mvn package {0}", packageFile));
        return sb.toString();
    }
}

package com.chinaunicom.software.origin.common.jenkins.script.node.steps.build;

import com.aio.portable.swiss.global.Constant;
import com.aio.portable.swiss.sugar.RegexSugar;
import com.aio.portable.swiss.suite.bean.BeanSugar;
import com.chinaunicom.software.origin.common.jenkins.script.node.Steps;
import lombok.Data;

import java.text.MessageFormat;
import java.util.Map;

@Data
public class StepsMaven extends Steps {



    private String module;
    private String workDirectory;
    private String outputFile;

    public final String getBlock() {
        StringBuilder sb = new StringBuilder();
        sb.append("cd ${workDirectory}" + Constant.LINE_SEPARATOR);
        sb.append("mvn package ${module}" + Constant.LINE_SEPARATOR);
        sb.append("export docker_source=${outputFile}" + Constant.LINE_SEPARATOR);
        String block = sb.toString();
        return block;
    }

}

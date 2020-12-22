package com.chinaunicom.software.origin.common.jenkins.script.node;

import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;

@Data
public class Stages extends ParentNode {
    public Stages() {
    }

    public Stages(Stage... stageArray) {
        setStageList(new ArrayList<>(Arrays.asList(stageArray)));
    }
}

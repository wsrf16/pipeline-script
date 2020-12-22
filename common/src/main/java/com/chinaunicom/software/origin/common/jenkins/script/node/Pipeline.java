package com.chinaunicom.software.origin.common.jenkins.script.node;

import com.chinaunicom.software.origin.common.jenkins.script.refer.NodeType;
import com.chinaunicom.software.origin.common.jenkins.script.refer.ScriptMark;
import lombok.Data;

@Data
public class Pipeline extends ParentNode {
    @ScriptMark(type = NodeType.Complex)
    private Triggers triggers;
}

package com.chinaunicom.software.origin.common.jenkins.script.node;

import com.chinaunicom.software.origin.common.jenkins.script.refer.NodeType;
import com.chinaunicom.software.origin.common.jenkins.script.refer.ScriptMark;
import lombok.Data;

@Data
public class Steps extends ParentNode {
    @ScriptMark(type = NodeType.Complex)
    private Parallel parallel;

    @ScriptMark(type = NodeType.Complex)
    private Script script;
}

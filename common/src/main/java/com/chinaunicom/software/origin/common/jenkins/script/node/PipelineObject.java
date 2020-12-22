package com.chinaunicom.software.origin.common.jenkins.script.node;

import com.chinaunicom.software.origin.common.jenkins.script.refer.NodeType;
import com.chinaunicom.software.origin.common.jenkins.script.refer.ScriptMark;
import lombok.Data;

@Data
public class PipelineObject extends ParentNode {
    @ScriptMark(type = NodeType.Ignore)
    private String templateId;

    @ScriptMark(name = "pipeline")
    private Pipeline pipeline;
}

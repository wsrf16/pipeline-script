package com.chinaunicom.software.origin.common.jenkins.script.refer;

import lombok.Data;

@Data
public class ScriptNode {
    private NodeType type;
    private String name;
    private Object object;

    public ScriptNode(NodeType type, String name, Object object) {
        this.type = type;
        this.name = name;
        this.object = object;
    }

}

package com.chinaunicom.software.origin.common.jenkins.script.refer;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface ScriptPlaceHolder {
    String name() default "";

    NodeType type() default NodeType.Complex;
}

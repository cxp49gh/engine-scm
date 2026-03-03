package com.engine.scm.other;


import java.util.List;

class ParamDef {
    String key;
    ParamType type;
    boolean required;

    Object defaultValue;

    Integer min;
    Integer max;
    String pattern;
    List<Object> enumValues;

    // list 专用
    String delimiter;
}

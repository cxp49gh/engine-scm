package com.engine.scm.dto;

public enum ParamType {

    STRING,
    INT,
    LONG,
    DOUBLE,
    BOOLEAN,

    /** 逗号分隔字符串，如 sceneIds */
    STRING_LIST,

    /** JSON Object */
    OBJECT,

    /** JSON Array */
    ARRAY
}
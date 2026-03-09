package com.engine.scm.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 参数类型枚举
 * 定义运行时参数的数据类型
 */
@Schema(description = "参数类型")
public enum ParamType {

    /** 字符串类型 */
    @Schema(description = "字符串类型")
    STRING,

    /** 整数类型 */
    @Schema(description = "整数类型")
    INT,

    /** 长整数类型 */
    @Schema(description = "长整数类型")
    LONG,

    /** 浮点数类型 */
    @Schema(description = "浮点数类型")
    DOUBLE,

    /** 布尔类型 */
    @Schema(description = "布尔类型")
    BOOLEAN,

    /** 逗号分隔字符串列表，如 sceneIds */
    @Schema(description = "逗号分隔字符串列表，如 sceneIds")
    STRING_LIST,

    /** JSON 对象类型 */
    @Schema(description = "JSON 对象类型")
    OBJECT,

    /** JSON 数组类型 */
    @Schema(description = "JSON 数组类型")
    ARRAY
}
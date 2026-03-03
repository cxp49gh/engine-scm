package com.engine.scm.other;

public enum ParamType {

    /* ========= 基础类型 ========= */

    STRING,
    NUMBER,
    BOOLEAN,

    /* ========= 枚举 / 选择 ========= */

    ENUM,              // 单选
    MULTI_ENUM,        // 多选（逗号 / 数组）

    /* ========= 结构类型 ========= */

    OBJECT,            // Map / JSON Object
    ARRAY,             // List

    /* ========= 业务语义类型 ========= */

    PATH,              // 文件 / HDFS / OSS 路径
    VERSION,           // 引擎 / 算法版本号
    BIZ_CODE,          // 业务编码
    LINK_CODE,         // 环节编码

    /* ========= 运行控制 ========= */

    RESOURCE,          // CPU / MEM / GPU
    TIMEOUT,           // 超时（秒 / 分）
    RETRY,             // 重试次数
    PARALLELISM,       // 并行度

    /* ========= 表达式 ========= */

    TEMPLATE_EXPR,     // freemarker 表达式
    PLACEHOLDER,       // ${xxx}

    /* ========= 高级 ========= */

    SECRET,            // 密钥 / Token（脱敏）
    JSON               // 任意 JSON（弱校验）
}

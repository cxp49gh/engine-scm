package com.engine.scm.utils;

import lombok.Getter;

/**
 * 自定义错误类型
 * @author jiang
 */
@Getter
public enum CustomError {
    NOT("0", "没有错误"),
    EXCEPTION("7", "异常"),
    IDS_IS_EMPTY("9", "id集合是空的"),
    UPDATES_HAS_FAILED("10", "更新有错误"),
    PARAM_IS_EMPTY("11", "参数是空的"),
    INVALID_TASK_FRAME_VERSION("13", "无效任务框版本"),
    NOT_FOUND_TASK_FRAME("19", "未找到任务框"),
    RESULT_IS_EMPTY("21", "结果是空的"),
    INVALID_TYPE("28", "无效类型"),
    TASKS_IS_EMPTY("36", "任务集合是空的"),
    INVALID_POLYGON_TYPE("39", ""),
    UNSUPPORTED("42", "不支持"),
    PROJECT_ID_IS_EMPTY("47", "projectId是空的"),
    BRANCH_NAME_IS_EMPTY("48", "分支名称是空的"),
    UNSUPPORTED_CHINESE("54", "不支持中文"),
    CONFLICTED("60", "冲突"),
    OCCUPIED("61", "被占用"),
    NOT_FOUND_ADJACENT_TASK_FRAMES("72", "未找到周边的任务框"),
    DATA_DUPLICATED("75", "数据重复"),


    UNKNOWN("9999", "占位置");

    private String code;
    private String message;

    CustomError(final String code, final String message) {
        this.code = code;
        this.message = message;
    }

    public CustomError setMessage(final String message) {
        this.message = message;
        return this;
    }
}

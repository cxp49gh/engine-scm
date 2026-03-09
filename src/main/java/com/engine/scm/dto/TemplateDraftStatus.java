package com.engine.scm.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 模板草稿状态枚举
 */
@Schema(description = "模板草稿状态")
public enum TemplateDraftStatus {

    /** 草稿状态 - 可编辑 */
    @Schema(description = "草稿状态 - 可编辑")
    DRAFT,

    /** 锁定状态 - 准备发布，不可编辑 */
    @Schema(description = "锁定状态 - 准备发布，不可编辑")
    LOCKED,

    /** 废弃状态 - 已弃用 */
    @Schema(description = "废弃状态 - 已弃用")
    DEPRECATED
}
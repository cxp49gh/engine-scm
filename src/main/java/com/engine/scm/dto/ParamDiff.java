package com.engine.scm.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 参数差异实体
 * 用于记录参数合并过程中的变更内容
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "参数差异")
public class ParamDiff {

    /** 参数名称 */
    @Schema(description = "参数名称", example = "timeout")
    private String paramName;

    /** 旧值（默认值） */
    @Schema(description = "旧值（默认值）")
    private Object oldValue;

    /** 新值（运行时覆盖值） */
    @Schema(description = "新值（运行时覆盖值）")
    private Object newValue;

    /** 风险等级 */
    @Schema(description = "风险等级", example = "LOW")
    private RiskLevel riskLevel;
}
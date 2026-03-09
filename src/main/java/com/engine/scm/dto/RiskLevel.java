package com.engine.scm.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 风险等级枚举
 * 用于评估参数变更的风险程度
 */
@Schema(description = "风险等级")
public enum RiskLevel {

    /** 低风险 - 普通参数变更，不影响系统行为 */
    @Schema(description = "低风险 - 普通参数变更，不影响系统行为")
    LOW,

    /** 中风险 - 影响资源使用或性能参数 */
    @Schema(description = "中风险 - 影响资源使用或性能参数")
    MEDIUM,

    /** 高风险 - 影响执行逻辑或成本的关键参数 */
    @Schema(description = "高风险 - 影响执行逻辑或成本的关键参数")
    HIGH
}
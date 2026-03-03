package com.engine.scm.template;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        name = "ParamMeta",
        description = "运行时参数元数据定义，用于参数校验、合并、风险评估"
)
public class ParamMeta {

    @Schema(
            description = "参数名（全局唯一，用于模板占位符）",
            example = "cpuNums",
            required = true
    )
    private String name;

    @Schema(
            description = "参数类型",
            required = true,
            example = "INT"
    )
    private ParamType type;

    @Schema(
            description = "是否必填（发布或运行时必须提供）",
            example = "true"
    )
    private boolean required;

    @Schema(
            description = "是否允许运行时覆盖默认值",
            example = "true"
    )
    private boolean overridable;

    @Schema(
            description = "参数默认值（可选，发布时作为 defaultParams 使用）",
            example = "10"
    )
    private Object defaultValue;

    @Schema(
            description = "枚举值约束（仅允许在此列表中的值）",
            example = "[\"LOW\", \"MEDIUM\", \"HIGH\"]"
    )
    private List<Object> enumValues;

    @Schema(
            description = "数值最小值（仅对 INT / DOUBLE 生效）",
            example = "1"
    )
    private Number min;

    @Schema(
            description = "数值最大值（仅对 INT / DOUBLE 生效）",
            example = "64"
    )
    private Number max;

    @Schema(
            description = "字符串格式校验正则（仅对 STRING / STRING_LIST 生效）",
            example = "^[a-zA-Z0-9_-]+$"
    )
    private String pattern;

    @Schema(
            description = "参数风险等级（用于发布审计、灰度控制）",
            example = "MEDIUM"
    )
    private RiskLevel riskLevel;

    @Schema(
            description = "参数说明（给配置人员看的描述）",
            example = "CPU 核数配置，影响资源使用"
    )
    private String description;
}
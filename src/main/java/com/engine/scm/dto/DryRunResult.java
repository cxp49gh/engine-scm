package com.engine.scm.dto;

import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
@Schema(description = "Dry-Run 执行结果")
public class DryRunResult {

    @Schema(description = "合并后的最终参数", example = "{\"timeout\": 3000, \"retries\": 3}")
    private Map<String, Object> mergedParams;

    @Schema(description = "渲染后的最终内容")
    private String renderedContent;

    @Schema(description = "参数 Diff 列表")
    private List<ParamDiff> paramDiffs;

    @Schema(description = "风险汇总")
    private RiskSummary riskSummary;

    @Schema(description = "是否通过", example = "true")
    private boolean passed;

    @Schema(description = "错误信息")
    private String errorMessage;
}
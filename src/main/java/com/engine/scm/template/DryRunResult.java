package com.engine.scm.template;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class DryRunResult {

    /** 合并后的最终参数 */
    private Map<String, Object> mergedParams;

    /** 渲染后的最终 JSON */
    private JsonNode renderedJson;

    /** 参数 Diff */
    private List<ParamDiff> paramDiffs;

    /** 风险汇总 */
    private RiskSummary riskSummary;

    /** 是否通过 */
    private boolean passed;

    /** 错误信息 */
    private String errorMessage;
}

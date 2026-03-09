package com.engine.scm.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 风险汇总
 * 统计参数变更中的高、中、低风险数量
 */
@Data
@Schema(description = "风险汇总")
public class RiskSummary {

    /** 高风险变更数量 */
    @Schema(description = "高风险变更数量", example = "0")
    private int high;

    /** 中风险变更数量 */
    @Schema(description = "中风险变更数量", example = "1")
    private int medium;

    /** 低风险变更数量 */
    @Schema(description = "低风险变更数量", example = "2")
    private int low;

    /**
     * 从参数差异列表构建风险汇总
     * @param diffs 参数差异列表
     * @return 风险汇总对象
     */
    public static RiskSummary fromDiffs(List<ParamDiff> diffs) {
        RiskSummary s = new RiskSummary();
        for (ParamDiff d : diffs) {
            if (d.getRiskLevel() == null) continue;
            switch (d.getRiskLevel()) {
                case HIGH:
                    s.high++;
                    break;
                case MEDIUM:
                    s.medium++;
                    break;
                case LOW:
                    s.low++;
                    break;
                default:
                    break;
            }
        }
        return s;
    }

    /**
     * 判断是否存在高风险变更
     * @return 是否存在高风险
     */
    public boolean hasHighRisk() {
        return high > 0;
    }
}
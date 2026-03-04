package com.engine.scm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class ParamMergeResult {

    /** 合并后的最终参数 */
    private Map<String, Object> mergedParams;

    /** 参数 Diff（用于 Dry-Run / 审计） */
    private List<ParamDiff> diffs;
}

package com.engine.scm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
public class ParamMergeResult {

    /** 合并后的最终参数 */
    private Map<String, Object> params;

    /** 参数 Diff（用于 Dry-Run / 审计） */
    private List<ParamDiff> diffs;
}

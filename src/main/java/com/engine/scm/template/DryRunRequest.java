package com.engine.scm.template;

import lombok.Data;

import java.util.Map;

@Data
public class DryRunRequest {

    /** 模板快照 ID */
    private String snapshotId;

    /** 运行时覆盖参数 */
    private Map<String, Object> runtimeParams;

    /** 是否严格校验（true = 有 warning 也失败） */
    private boolean strict;
}

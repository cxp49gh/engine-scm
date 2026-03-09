package com.engine.scm.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Map;

@Data
@Schema(description = "Dry-Run 请求")
public class DryRunRequest {

    @Schema(description = "模板快照 ID", required = true, example = "64f8a2b3c9e1a1234567890a")
    private String snapshotId;

    @Schema(description = "运行时覆盖参数", example = "{\"region\": \"us-east-1\", \"env\": \"prod\"}")
    private Map<String, Object> runtimeParams;

    @Schema(description = "是否严格校验（true = 有 warning 也失败）", example = "false")
    private boolean strict;
}
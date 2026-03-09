package com.engine.scm.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 运行时上下文
 * 用于在参数合并过程中传递调用环境信息
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "运行时上下文")
public class RuntimeContext {

    /** ========== 业务维度 ========== */

    /** 业务编码，如 payment/order/delivery */
    @Schema(description = "业务编码", example = "payment")
    private String bizCode;

    /** 执行环节，如 pre-check/post-process */
    @Schema(description = "执行环节", example = "pre-check")
    private String linkCode;

    /** 引擎编码，如 order-engine/payment-engine */
    @Schema(description = "引擎编码", example = "order-engine")
    private String engineCode;

    /** ========== 版本维度 ========== */

    /** 引擎版本 */
    @Schema(description = "引擎版本", example = "v2.1.0")
    private String engineVersion;

    /** 模板版本 */
    @Schema(description = "模板版本", example = "2024.01.15")
    private String templateVersion;

    /** ========== 调用来源 ========== */

    /** 环境：dev/test/prod */
    @Schema(description = "环境", example = "prod")
    private String env;

    /** 调用方：scheduler/manual/api */
    @Schema(description = "调用方", example = "manual")
    private String caller;

    /** 操作人：userId 或 system */
    @Schema(description = "操作人", example = "admin")
    private String operator;

    /** ========== 执行维度 ========== */

    /** 请求 ID */
    @Schema(description = "请求 ID", example = "req-123456")
    private String requestId;

    /** 链路追踪 ID */
    @Schema(description = "链路追踪 ID", example = "trace-789012")
    private String traceId;

    /** 是否为 Dry-Run 模式 */
    @Schema(description = "是否为 Dry-Run 模式", example = "false")
    private boolean dryRun;

    /** ========== 时间维度 ========== */

    /** 触发时间 */
    @Schema(description = "触发时间")
    private Instant triggerTime;

    /** ========== 扩展字段 ========== */

    /** 扩展信息 */
    @Schema(description = "扩展信息")
    private Map<String, Object> ext;

    /**
     * 转换为 Map 用于参数合并
     * @return 包含上下文信息的 Map
     */
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();

        map.put("bizCode", bizCode);
        map.put("linkCode", linkCode);
        map.put("engineCode", engineCode);

        map.put("engineVersion", engineVersion);
        map.put("templateVersion", templateVersion);

        map.put("env", env);
        map.put("caller", caller);
        map.put("operator", operator);

        map.put("requestId", requestId);
        map.put("traceId", traceId);

        map.put("triggerTime", triggerTime != null
                ? triggerTime.toString()
                : null);

        if (ext != null) {
            map.putAll(ext);
        }
        return map;
    }
}
package com.engine.scm.template;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RuntimeContext {

    /** ========== 业务维度 ========== */
    private String bizCode;
    private String linkCode;
    private String engineCode;

    /** ========== 版本维度 ========== */
    private String engineVersion;
    private String templateVersion;

    /** ========== 调用来源 ========== */
    private String env;          // dev / test / prod
    private String caller;       // scheduler / manual / api
    private String operator;     // userId / system

    /** ========== 执行维度 ========== */
    private String requestId;
    private String traceId;
    private boolean dryRun;

    /** ========== 时间维度 ========== */
    private Instant triggerTime;

    /** ========== 扩展 ========== */
    private Map<String, Object> ext;

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

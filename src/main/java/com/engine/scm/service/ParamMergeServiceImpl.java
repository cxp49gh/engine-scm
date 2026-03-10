package com.engine.scm.service;

import com.engine.scm.domain.RuntimeContext;
import com.engine.scm.dto.ParamDiff;
import com.engine.scm.dto.ParamMergeResult;
import com.engine.scm.dto.ParamMeta;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Service
public class ParamMergeServiceImpl implements ParamMergeService {

    @Override
    public ParamMergeResult merge(
            List<ParamMeta> params,
            Map<String, Object> runtimeOverrides
    ) {
        // 从 params 中提取默认值
        Map<String, Object> defaults = new HashMap<>();
        if (params != null) {
            for (ParamMeta param : params) {
                if (param.getDefaultValue() != null) {
                    defaults.put(param.getName(), param.getDefaultValue());
                }
            }
        }

        // 计算 Diff：运行时参数覆盖了哪些默认值
        List<ParamDiff> diffs = new ArrayList<>();
        if (runtimeOverrides != null && !runtimeOverrides.isEmpty()) {
            for (Map.Entry<String, Object> entry : runtimeOverrides.entrySet()) {
                Object defaultValue = defaults.get(entry.getKey());
                diffs.add(ParamDiff.builder()
                        .paramName(entry.getKey())
                        .oldValue(defaultValue)
                        .newValue(entry.getValue())
                        .build());
            }
        }

        // 合并：默认值 + 运行时参数（运行时参数覆盖默认值）
        Map<String, Object> merged = merge(defaults, runtimeOverrides, null);
        return ParamMergeResult.builder()
                .params(merged)
                .diffs(diffs)
                .build();
    }

    @Override
    public Map<String, Object> merge(
            Map<String, Object> defaults,
            Map<String, Object> overrides,
            RuntimeContext ctx
    ) {
        Map<String, Object> result = new HashMap<>();

        if (ctx != null) {
            result.putAll(ctx.toMap());
        }
        if (defaults != null) {
            result.putAll(defaults);
        }
        if (overrides != null) {
            result.putAll(overrides);
        }
        return result;
    }
}

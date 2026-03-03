package com.engine.scm.template;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ParamMergeServiceImpl implements ParamMergeService {

    @Override
    public ParamMergeResult merge(
            RuntimeParamDefinition paramDef,
            Map<String, Object> templateDefaults,
            Map<String, Object> runtimeOverrides) {

        Map<String, Object> result = new HashMap<>();
        List<ParamDiff> diffs = new ArrayList<>();

        Map<String, ParamMeta> metaMap = paramDef.getParams()
                .stream()
                .collect(Collectors.toMap(ParamMeta::getName, m -> m));

        // 1️⃣ 初始化：ParamMeta.defaultValue
        for (ParamMeta meta : metaMap.values()) {
            if (meta.getDefaultValue() != null) {
                result.put(meta.getName(), meta.getDefaultValue());
            }
        }

        // 2️⃣ 覆盖：Template 默认参数
        if (templateDefaults != null) {
            for (Map.Entry<String, Object> e : templateDefaults.entrySet()) {
                assertKnownParam(e.getKey(), metaMap);
                result.put(e.getKey(), e.getValue());
            }
        }

        // 3️⃣ 覆盖：Runtime 参数（需校验 overridable）
        if (runtimeOverrides != null) {
            for (Map.Entry<String, Object> e : runtimeOverrides.entrySet()) {

                String key = e.getKey();
                Object newVal = e.getValue();

                ParamMeta meta = metaMap.get(key);
                assertKnownParam(key, metaMap);

                if (!meta.isOverridable()) {
                    throw BizException.invalid(
                            "Param not overridable: " + key);
                }

                Object oldVal = result.get(key);
                if (!Objects.equals(oldVal, newVal)) {
                    diffs.add(new ParamDiff(
                            key, oldVal, newVal, meta.getRiskLevel()));
                }

                result.put(key, newVal);
            }
        }

        return new ParamMergeResult(result, diffs);
    }

    private void assertKnownParam(String name, Map<String, ParamMeta> metaMap) {
        if (!metaMap.containsKey(name)) {
            throw BizException.invalid("Unknown param: " + name);
        }
    }
}

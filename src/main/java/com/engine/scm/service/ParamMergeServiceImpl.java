package com.engine.scm.service;

import com.engine.scm.domain.RuntimeContext;
import com.engine.scm.domain.RuntimeParamDefinition;
import com.engine.scm.dto.ParamMergeResult;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
@Service
public class ParamMergeServiceImpl implements ParamMergeService {

    @Override
    public ParamMergeResult merge(
            RuntimeParamDefinition paramDef,
            Map<String, Object> templateDefaults,
            Map<String, Object> runtimeOverrides
    ) {
        Map<String, Object> merged = merge(templateDefaults, runtimeOverrides, null);
        return ParamMergeResult.builder()
                .params(merged)
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

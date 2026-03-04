package com.engine.scm.template;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
@Service
public class ParamMergeServiceImpl {

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

package com.engine.scm.service;

import com.engine.scm.domain.RuntimeContext;
import com.engine.scm.domain.RuntimeParamDefinition;
import com.engine.scm.dto.ParamMergeResult;

import java.util.Map;

public interface ParamMergeService {

    ParamMergeResult merge(
            RuntimeParamDefinition paramDef,
            Map<String, Object> templateDefaults,
            Map<String, Object> runtimeOverrides
    );

    Map<String, Object> merge(
            Map<String, Object> defaults,
            Map<String, Object> overrides,
            RuntimeContext ctx
    );
}

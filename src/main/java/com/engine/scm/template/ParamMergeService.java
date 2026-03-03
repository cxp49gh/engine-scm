package com.engine.scm.template;

import java.util.Map;

public interface ParamMergeService {

    ParamMergeResult merge(
            RuntimeParamDefinition paramDef,
            Map<String, Object> templateDefaults,
            Map<String, Object> runtimeOverrides
    );
}

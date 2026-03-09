package com.engine.scm.service;

import com.engine.scm.domain.RuntimeContext;
import com.engine.scm.dto.ParamMergeResult;
import com.engine.scm.dto.ParamMeta;

import java.util.List;
import java.util.Map;

public interface ParamMergeService {

    /**
     * 合并参数：默认值 + 运行时参数
     * 默认值从 params 中的 defaultValue 提取
     */
    ParamMergeResult merge(
            List<ParamMeta> params,
            Map<String, Object> runtimeOverrides
    );

    Map<String, Object> merge(
            Map<String, Object> defaults,
            Map<String, Object> overrides,
            RuntimeContext ctx
    );
}

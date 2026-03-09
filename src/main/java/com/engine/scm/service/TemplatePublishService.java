package com.engine.scm.service;

import com.engine.scm.domain.RuntimeContext;
import com.engine.scm.domain.TemplateSnapshot;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Map;

public interface TemplatePublishService {

    TemplateSnapshot publish(
            String draftId,
            String newVersion,
            Map<String, Object> runtimeOverrides,
            RuntimeContext runtimeContext
    ) throws JsonProcessingException;
}

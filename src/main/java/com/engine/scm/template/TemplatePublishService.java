package com.engine.scm.template;

import java.util.Map;

public interface TemplatePublishService {

    TemplateSnapshot publish(
            String draftId,
            String newVersion,
            Map<String, Object> runtimeOverrides,
            RuntimeContext runtimeContext
    );
}

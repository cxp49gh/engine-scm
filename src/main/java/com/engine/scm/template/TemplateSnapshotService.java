package com.engine.scm.template;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public interface TemplateSnapshotService {

    TemplateSnapshot publish(
            TemplateDraft draft,
            String version,
            JsonNode diffFromPrev,
            String riskLevel
    );

    TemplateSnapshot getById(String id);

    TemplateSnapshot getByTemplateAndVersion(String templateId, String version);

    TemplateSnapshot getLatest(String templateId);

    List<TemplateSnapshot> listByTemplate(String templateId);
}

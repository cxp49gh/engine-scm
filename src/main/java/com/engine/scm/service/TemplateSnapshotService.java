package com.engine.scm.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.engine.scm.domain.TemplateDraft;
import com.engine.scm.domain.TemplateSnapshot;

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

    List<TemplateSnapshot> listAll();

    List<TemplateSnapshot> list(String templateId, String bizCode, String linkCode, String name);
}

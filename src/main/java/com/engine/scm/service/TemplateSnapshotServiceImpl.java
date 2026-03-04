package com.engine.scm.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.engine.scm.domain.TemplateDraft;
import com.engine.scm.domain.TemplateSnapshot;
import com.engine.scm.exception.BizException;
import com.engine.scm.repository.TemplateSnapshotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TemplateSnapshotServiceImpl
        implements TemplateSnapshotService {

    private final TemplateSnapshotRepository repository;

    @Override
    public TemplateSnapshot publish(
            TemplateDraft draft,
            String version,
            JsonNode diffFromPrev,
            String riskLevel
    ) {

        repository.findByTemplateIdAndVersion(draft.getId(), version)
                .ifPresent(s -> {
                    throw BizException.conflict(
                            "Snapshot version already exists: " + version);
                });

        TemplateSnapshot snapshot = new TemplateSnapshot();
        snapshot.setTemplateId(draft.getId());
        snapshot.setVersion(version);
        snapshot.setTemplateContent(draft.getTemplateContent());
        snapshot.setDefaultParams(draft.getDefaultParams());
        snapshot.setDiffFromPrev(diffFromPrev);
        snapshot.setRiskLevel(riskLevel);
        snapshot.setPublishedAt(Instant.now());

        return repository.save(snapshot);
    }

    @Override
    public TemplateSnapshot getById(String id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        BizException.notFound("Snapshot not found"));
    }

    @Override
    public TemplateSnapshot getByTemplateAndVersion(
            String templateId,
            String version
    ) {
        return repository.findByTemplateIdAndVersion(templateId, version)
                .orElseThrow(() ->
                        BizException.notFound("Snapshot not found"));
    }

    @Override
    public TemplateSnapshot getLatest(String templateId) {
        return repository.findTopByTemplateIdOrderByPublishedAtDesc(templateId)
                .orElseThrow(() ->
                        BizException.notFound("No snapshot published"));
    }

    @Override
    public List<TemplateSnapshot> listByTemplate(String templateId) {
        return repository.findByTemplateIdOrderByPublishedAtDesc(templateId);
    }
}

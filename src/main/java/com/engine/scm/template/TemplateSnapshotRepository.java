package com.engine.scm.template;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface TemplateSnapshotRepository
        extends MongoRepository<TemplateSnapshot, String> {

    List<TemplateSnapshot> findByTemplateIdOrderByPublishedAtDesc(String templateId);

    Optional<TemplateSnapshot> findTopByTemplateIdOrderByPublishedAtDesc(String templateId);

    Optional<TemplateSnapshot> findByTemplateIdAndVersion(
            String templateId,
            String version
    );
}

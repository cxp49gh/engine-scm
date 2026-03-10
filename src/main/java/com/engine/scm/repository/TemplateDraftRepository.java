package com.engine.scm.repository;

import com.engine.scm.domain.TemplateDraft;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface TemplateDraftRepository
        extends MongoRepository<TemplateDraft, String> {

    Optional<TemplateDraft> findByBizCodeAndEngineCodeAndLinkCode(
            String bizCode,
            String engineCode,
            String linkCode
    );

    List<TemplateDraft> findByBizCodeAndStatus(
            String bizCode,
            String status
    );

    List<TemplateDraft> findByLinkCode(String linkCode);

    List<TemplateDraft> findByNameContaining(String name);
}

package com.engine.scm.template;

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
}

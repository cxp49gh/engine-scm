package com.engine.scm.service;

import com.engine.scm.domain.TemplateDraft;
import com.engine.scm.dto.TemplateDraftStatus;

import java.util.List;

public interface TemplateDraftService {

    TemplateDraft create(TemplateDraft draft);

    TemplateDraft getById(String id);

    List<TemplateDraft> list(String bizCode, TemplateDraftStatus status);

    TemplateDraft update(String id, TemplateDraft update);

    TemplateDraft lock(String id);

    void delete(String id);

    void updateCurrentVersion(String draftId, String version);

}

package com.engine.scm.service;

import com.engine.scm.domain.TemplateDraft;
import com.engine.scm.dto.TemplateDraftStatus;
import com.engine.scm.exception.BizException;
import com.engine.scm.repository.TemplateDraftRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TemplateDraftServiceImpl implements TemplateDraftService {

    private final TemplateDraftRepository repository;

    @Override
    public TemplateDraft create(TemplateDraft draft) {

        repository.findByBizCodeAndEngineCodeAndLinkCode(
                draft.getBizCode(),
                draft.getEngineCode(),
                draft.getLinkCode()
        ).ifPresent(d -> {
            throw BizException.conflict("Template draft already exists");
        });

        draft.setId(null);
        draft.setStatus(TemplateDraftStatus.DRAFT.name());
        draft.setCreatedAt(Instant.now());
        draft.setUpdatedAt(Instant.now());

        return repository.save(draft);
    }

    @Override
    public TemplateDraft getById(String id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        BizException.notFound("Template draft not found"));
    }

    @Override
    public List<TemplateDraft> list(String bizCode, TemplateDraftStatus status) {
        if (bizCode != null && status != null) {
            return repository.findByBizCodeAndStatus(
                    bizCode, status.name());
        }
        return repository.findAll();
    }

    @Override
    public TemplateDraft update(String id, TemplateDraft update) {

        TemplateDraft draft = getById(id);

        if (!TemplateDraftStatus.DRAFT.name().equals(draft.getStatus())) {
            throw BizException.invalid("Only DRAFT can be updated");
        }

        draft.setName(update.getName());
        draft.setDescription(update.getDescription());
        draft.setTemplateContent(update.getTemplateContent());
        draft.setDefaultParams(update.getDefaultParams());
        draft.setRuntimeParamDefRef(update.getRuntimeParamDefRef());
        draft.setUpdatedAt(Instant.now());

        return repository.save(draft);
    }

    @Override
    public TemplateDraft lock(String id) {

        TemplateDraft draft = getById(id);

        if (!TemplateDraftStatus.DRAFT.name().equals(draft.getStatus())) {
            throw BizException.invalid("Only DRAFT can be locked");
        }

        draft.setStatus(TemplateDraftStatus.LOCKED.name());
        draft.setUpdatedAt(Instant.now());

        return repository.save(draft);
    }

    @Override
    public void delete(String id) {

        TemplateDraft draft = getById(id);

        if (!TemplateDraftStatus.DRAFT.name().equals(draft.getStatus())) {
            throw BizException.invalid("Only DRAFT can be deleted");
        }

        repository.deleteById(id);
    }




    @Override
    public void updateCurrentVersion(String draftId, String version) {

        TemplateDraft draft = getById(draftId);

        if (!TemplateDraftStatus.LOCKED.name().equals(draft.getStatus())) {
            throw BizException.invalid("Only LOCKED draft can be published");
        }

        draft.setCurrentVersion(version);
        // ❌ 不改状态
        draft.setUpdatedAt(Instant.now());

        repository.save(draft);
    }

}

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
    public List<TemplateDraft> list(String bizCode, String linkCode, String name, TemplateDraftStatus status) {
        List<TemplateDraft> drafts = repository.findAll();

        // 过滤条件
        if (bizCode != null && !bizCode.isEmpty()) {
            drafts = drafts.stream()
                    .filter(d -> d.getBizCode() != null && d.getBizCode().equals(bizCode))
                    .collect(java.util.stream.Collectors.toList());
        }
        if (linkCode != null && !linkCode.isEmpty()) {
            drafts = drafts.stream()
                    .filter(d -> d.getLinkCode() != null && d.getLinkCode().equals(linkCode))
                    .collect(java.util.stream.Collectors.toList());
        }
        if (name != null && !name.isEmpty()) {
            drafts = drafts.stream()
                    .filter(d -> d.getName() != null && d.getName().contains(name))
                    .collect(java.util.stream.Collectors.toList());
        }
        if (status != null) {
            drafts = drafts.stream()
                    .filter(d -> d.getStatus() != null && d.getStatus().equals(status.name()))
                    .collect(java.util.stream.Collectors.toList());
        }

        return drafts;
    }

    @Override
    public TemplateDraft update(String id, TemplateDraft update) {

        TemplateDraft draft = getById(id);

        // 模板内容允许 DRAFT 和 LOCKED 状态更新
        if (!TemplateDraftStatus.DRAFT.name().equals(draft.getStatus())
            && !TemplateDraftStatus.LOCKED.name().equals(draft.getStatus())) {
            throw BizException.invalid("草稿状态不允许更新");
        }

        // 部分更新：只更新非 null 的字段
        if (update.getName() != null) {
            draft.setName(update.getName());
        }
        if (update.getDescription() != null) {
            draft.setDescription(update.getDescription());
        }
        if (update.getTemplateContent() != null) {
            draft.setTemplateContent(update.getTemplateContent());
        }
        if (update.getParams() != null) {
            draft.setParams(update.getParams());
        }
        if (update.getCurrentVersion() != null) {
            draft.setCurrentVersion(update.getCurrentVersion());
        }
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
    public TemplateDraft unlock(String id) {

        TemplateDraft draft = getById(id);

        if (!TemplateDraftStatus.LOCKED.name().equals(draft.getStatus())) {
            throw BizException.invalid("Only LOCKED can be unlocked");
        }

        // 已发布的草稿不允许解锁
        if (draft.getCurrentVersion() != null && !draft.getCurrentVersion().isEmpty()) {
            throw BizException.invalid("Published draft cannot be unlocked");
        }

        draft.setStatus(TemplateDraftStatus.DRAFT.name());
        draft.setUpdatedAt(Instant.now());

        return repository.save(draft);
    }

    @Override
    public void delete(String id) {

        TemplateDraft draft = getById(id);

        // 只有未发布的 DRAFT 状态才能删除
        if (!TemplateDraftStatus.DRAFT.name().equals(draft.getStatus())) {
            throw BizException.invalid("Only DRAFT can be deleted");
        }

        // 已发布的草稿不允许删除
        if (draft.getCurrentVersion() != null && !draft.getCurrentVersion().isEmpty()) {
            throw BizException.invalid("Published draft cannot be deleted");
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

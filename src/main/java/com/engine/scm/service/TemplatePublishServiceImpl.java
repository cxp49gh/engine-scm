package com.engine.scm.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.engine.scm.domain.TemplateDraft;
import com.engine.scm.domain.TemplateSnapshot;
import com.engine.scm.domain.RuntimeContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TemplatePublishServiceImpl
        implements TemplatePublishService {

    private final TemplateDraftService draftService;
    private final TemplateSnapshotService snapshotService;
    private final ObjectMapper objectMapper;

    @Override
    public TemplateSnapshot publish(
            String draftId,
            String newVersion,
            Map<String, Object> runtimeOverrides,
            RuntimeContext runtimeContext
    ) throws JsonProcessingException {

        // 1. 获取草稿
        TemplateDraft draft = draftService.getById(draftId);

        // 2. 检查是否已有发布版本
        Optional<TemplateSnapshot> prevSnapshot =
                snapshotService.listByTemplate(draftId)
                        .stream()
                        .findFirst();

        // 3. 风险评估（首次发布为LOW，后续发布为MEDIUM表示有更新）
        String riskLevel = prevSnapshot.isPresent() ? "MEDIUM" : "LOW";

        // 4. 保存快照
        TemplateSnapshot snapshot = snapshotService.publish(
                draft,
                newVersion,
                objectMapper.createObjectNode(),
                riskLevel
        );

        // 5. 更新草稿的当前版本号
        draftService.updateCurrentVersion(draftId, newVersion);

        return snapshot;
    }
}

package com.engine.scm.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.engine.scm.util.FreemarkerHelper;
import com.engine.scm.util.JsonSchemaValidator;
import com.engine.scm.util.JsonDiffService;
import com.engine.scm.domain.TemplateDraft;
import com.engine.scm.domain.TemplateSnapshot;
import com.engine.scm.domain.RuntimeContext;
import com.engine.scm.dto.ParamMergeResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TemplatePublishServiceImpl
        implements TemplatePublishService {

    private final TemplateDraftService draftService;
    private final TemplateSnapshotService snapshotService;

    private final FreemarkerHelper freemarkerHelper;
    private final ParamMergeService paramMergeService;
    private final JsonSchemaValidator schemaValidator;
    private final JsonDiffService jsonDiffService;
    private final RiskAssessService riskAssessService;

    private final ObjectMapper objectMapper;

    @Override
    public TemplateSnapshot publish(
            String draftId,
            String newVersion,
            Map<String, Object> runtimeOverrides,
            RuntimeContext runtimeContext
    ) throws JsonProcessingException {

        // 1. Draft
        TemplateDraft draft = draftService.getById(draftId);

        // 2. 上一版本
        Optional<TemplateSnapshot> prevSnapshot =
                snapshotService.listByTemplate(draftId)
                        .stream()
                        .findFirst();

        // 3. 参数合并（从 params 提取默认值）
        ParamMergeResult mergeResult =
                paramMergeService.merge(
                        draft.getParams(),
                        runtimeOverrides
                );
        Map<String, Object> mergedParams = mergeResult.getParams();

        // 4. Dry-Run（Freemarker）
        String renderedContent = freemarkerHelper.render(
                draft.getTemplateContent(),
                mergedParams
        );

        // 5. Diff（渲染结果为字符串，无法做 JSON Diff）
        JsonNode diff;
        if (prevSnapshot.isPresent()) {
            // 上一版本的内容作为字符串比较
            String prevContent = prevSnapshot.get().getTemplateContent();
            Map<String, String> diffMap = new HashMap<>();
            diffMap.put("prevContent", prevContent != null ? prevContent : "");
            diffMap.put("renderedContent", renderedContent);
            diff = objectMapper.valueToTree(diffMap);
        } else {
            diff = objectMapper.createObjectNode();
        }

        // 6. 风险评估（基于是否有变化）
        String riskLevel = "0".equals(diff.toString()) || diff.isEmpty() ? "LOW" : "MEDIUM";

        // 8. Snapshot
        TemplateSnapshot snapshot = snapshotService.publish(
                draft,
                newVersion,
                diff,
                riskLevel
        );

        // 9. 更新 Draft
        draftService.updateCurrentVersion(draftId, newVersion);

        return snapshot;
    }
}

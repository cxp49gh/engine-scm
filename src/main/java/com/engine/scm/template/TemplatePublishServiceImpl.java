package com.engine.scm.template;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.engine.scm.template.ParamMergeServiceImpl;
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

    private final FreemarkerHelper freemarkerHelper;
    private final ParamMergeServiceImpl paramMergeService;
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
    ) {

        // 1. Draft
        TemplateDraft draft = draftService.getById(draftId);

        // 2. 上一版本
        Optional<TemplateSnapshot> prevSnapshot =
                snapshotService.listByTemplate(draftId)
                        .stream()
                        .findFirst();

        // 3. 参数合并
        Map<String, Object> mergedParams =
                paramMergeService.merge(
                        draft.getDefaultParams(),
                        runtimeOverrides,
                        runtimeContext
                );

        // 4. Dry-Run（Freemarker）
        JsonNode renderedJson = freemarkerHelper.renderJson(
                draft.getTemplateContent(),
                mergedParams
        );

        // 5. Schema 校验
        schemaValidator.validate(renderedJson);

        // 6. Diff
        JsonNode diff = prevSnapshot
                .map(prev ->
                        jsonDiffService.diff(
                                prev.getTemplateContent(),
                                renderedJson))
                .orElse(objectMapper.createObjectNode());

        // 7. 风险评估
        String riskLevel = riskAssessService.assess(diff);

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

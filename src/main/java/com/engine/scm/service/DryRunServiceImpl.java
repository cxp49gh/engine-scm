package com.engine.scm.service;

import com.engine.scm.domain.RuntimeParamDefinition;
import com.engine.scm.domain.TemplateSnapshot;
import com.engine.scm.dto.DryRunRequest;
import com.engine.scm.dto.DryRunResult;
import com.engine.scm.dto.ParamMergeResult;
import com.engine.scm.dto.RiskSummary;
import com.engine.scm.exception.BizException;
import com.engine.scm.repository.TemplateSnapshotRepository;
import com.engine.scm.util.FreemarkerHelper;
import com.engine.scm.util.JsonSchemaValidator;
import com.engine.scm.util.ParamValidator;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DryRunServiceImpl implements DryRunService {

    @Autowired
    private TemplateSnapshotRepository snapshotRepository;

    @Autowired
    private RuntimeParamDefService paramDefService;

    @Autowired
    private ParamMergeService paramMergeService;

    @Autowired
    private ParamValidator paramValidator;

    @Autowired
    private FreemarkerHelper freemarkerHelper;

    @Autowired
    private JsonSchemaValidator schemaValidator;

    @Override
    public DryRunResult dryRun(DryRunRequest request) {

        try {
            // 1️⃣ 加载模板快照
            TemplateSnapshot snapshot =
                    snapshotRepository.findById(request.getSnapshotId())
                            .orElseThrow(() ->
                                    BizException.invalid("Template snapshot not found"));

            // 2️⃣ 加载参数定义
            RuntimeParamDefinition paramDef =
                    paramDefService.get(snapshot.getRuntimeParamDefRef());

            // 3️⃣ 参数合并 + Diff
            ParamMergeResult mergeResult =
                    paramMergeService.merge(
                            paramDef,
                            snapshot.getDefaultParams(),
                            request.getRuntimeParams()
                    );

            // 4️⃣ 参数规则校验
            paramValidator.validate(
                    mergeResult.getMergedParams(), paramDef);

            // 5️⃣ Freemarker 渲染
            JsonNode renderedJson =
                    freemarkerHelper.renderJson(
                            snapshot.getTemplateContent(),
                            mergeResult.getMergedParams()
                    );

            // 6️⃣ JSON Schema 校验
            schemaValidator.validate(renderedJson);

            // 7️⃣ 风险汇总
            RiskSummary riskSummary =
                    RiskSummary.fromDiffs(mergeResult.getDiffs());

            // 8️⃣ 严格模式处理
            if (request.isStrict() && riskSummary.hasHighRisk()) {
                throw BizException.invalid(
                        "High risk params detected in strict mode");
            }

            return DryRunResult.builder()
                    .mergedParams(mergeResult.getMergedParams())
                    .renderedJson(renderedJson)
                    .paramDiffs(mergeResult.getDiffs())
                    .riskSummary(riskSummary)
                    .passed(true)
                    .build();

        } catch (Exception e) {

            return DryRunResult.builder()
                    .passed(false)
                    .errorMessage(e.getMessage())
                    .build();
        }
    }
}

package com.engine.scm.service;

import com.engine.scm.domain.TemplateSnapshot;
import com.engine.scm.dto.DryRunRequest;
import com.engine.scm.dto.DryRunResult;
import com.engine.scm.dto.ParamMergeResult;
import com.engine.scm.dto.ParamMeta;
import com.engine.scm.dto.RiskSummary;
import com.engine.scm.exception.BizException;
import com.engine.scm.repository.TemplateSnapshotRepository;
import com.engine.scm.util.FreemarkerHelper;
import com.engine.scm.util.ParamValidator;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DryRunServiceImpl implements DryRunService {

    @Autowired
    private TemplateSnapshotRepository snapshotRepository;

    @Autowired
    private ParamMergeService paramMergeService;

    @Autowired
    private ParamValidator paramValidator;

    @Autowired
    private FreemarkerHelper freemarkerHelper;

    @Override
    public DryRunResult dryRun(DryRunRequest request) throws JsonProcessingException {

        try {
            // 1️⃣ 加载模板快照
            TemplateSnapshot snapshot =
                    snapshotRepository.findById(request.getSnapshotId())
                            .orElseThrow(() ->
                                    BizException.invalid("Template snapshot not found"));

            // 2️⃣ 从快照获取参数定义
            List<ParamMeta> params = snapshot.getParams();

            // 3️⃣ 参数合并 + Diff (从 params 提取默认值)
            ParamMergeResult mergeResult =
                    paramMergeService.merge(
                            params,
                            request.getRuntimeParams()
                    );

            // 4️⃣ 参数规则校验
            paramValidator.validate(
                    mergeResult.getParams(), params);

            // 5️⃣ Freemarker 渲染
            String renderedContent =
                    freemarkerHelper.render(
                            snapshot.getTemplateContent(),
                            mergeResult.getParams()
                    );

            // 6️⃣ 风险汇总
            RiskSummary riskSummary =
                    RiskSummary.fromDiffs(mergeResult.getDiffs());

            // 7️⃣ 严格模式处理
            if (request.isStrict() && riskSummary.hasHighRisk()) {
                throw BizException.invalid(
                        "High risk params detected in strict mode");
            }

            return DryRunResult.builder()
                    .mergedParams(mergeResult.getParams())
                    .renderedContent(renderedContent)
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

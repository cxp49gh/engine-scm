package com.engine.scm.controller;

import com.engine.scm.domain.RuntimeContexts;
import com.engine.scm.domain.TemplateSnapshot;
import com.engine.scm.service.TemplatePublishService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/templates/publish")
@Tag(name = "Template Publish API", description = "模板发布接口")
@RequiredArgsConstructor
public class TemplatePublishController {

    private final TemplatePublishService publishService;

    @PostMapping("/{draftId}")
    @Operation(
            summary = "发布模板",
            description = "将草稿发布为新版本，创建快照。不指定版本号时自动递增"
    )
    public TemplateSnapshot publish(
            @Parameter(description = "草稿 ID", required = true) @PathVariable String draftId,
            @Parameter(description = "版本号，不指定则自动递增", required = false) @RequestParam(required = false) String version,
            @Parameter(description = "运行时覆盖参数") @RequestBody(required = false) Map<String, Object> overrides
    ) throws JsonProcessingException {
        return publishService.publish(
                draftId,
                version,
                overrides,
                RuntimeContexts.system()
        );
    }
}
package com.engine.scm.controller;

import com.engine.scm.dto.DryRunRequest;
import com.engine.scm.dto.DryRunResult;
import com.engine.scm.service.DryRunService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/template/dry-run")
@Tag(name = "Dry-Run API", description = "模板 Dry-Run 测试接口")
public class DryRunController {

    @Autowired
    private DryRunService dryRunService;

    @PostMapping
    @Operation(summary = "Dry-Run 测试", description = "使用指定的运行时参数渲染模板，并进行 Schema 校验和风险评估")
    public DryRunResult dryRun(@RequestBody DryRunRequest request) throws JsonProcessingException {
        return dryRunService.dryRun(request);
    }
}
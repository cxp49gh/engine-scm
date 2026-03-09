package com.engine.scm.controller;

import com.engine.scm.domain.TemplateSnapshot;
import com.engine.scm.service.TemplateSnapshotService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/templates/snapshots")
@Tag(name = "Template Snapshot API", description = "模板快照管理接口")
@RequiredArgsConstructor
public class TemplateSnapshotController {

    private final TemplateSnapshotService snapshotService;

    @GetMapping("/{id}")
    @Operation(summary = "获取快照", description = "根据 ID 获取模板快照详情")
    public TemplateSnapshot get(@Parameter(description = "快照 ID", required = true) @PathVariable String id) {
        return snapshotService.getById(id);
    }

    @GetMapping
    @Operation(summary = "查询快照列表", description = "根据模板 ID 查询所有快照")
    public List<TemplateSnapshot> listByTemplate(
            @Parameter(description = "模板/草稿 ID", required = false) @RequestParam(required = false) String templateId
    ) {
        if (templateId != null && !templateId.isEmpty()) {
            return snapshotService.listByTemplate(templateId);
        } else {
            return snapshotService.listAll();
        }
    }

    @GetMapping("/latest")
    @Operation(summary = "获取最新快照", description = "获取指定模板的最新版本快照")
    public TemplateSnapshot latest(
            @Parameter(description = "模板/草稿 ID", required = true) @RequestParam String templateId
    ) {
        return snapshotService.getLatest(templateId);
    }

    @GetMapping("/by-version")
    @Operation(summary = "按版本查询快照", description = "根据模板 ID 和版本号查询快照")
    public TemplateSnapshot byVersion(
            @Parameter(description = "模板/草稿 ID", required = true) @RequestParam String templateId,
            @Parameter(description = "版本号", required = true) @RequestParam String version
    ) {
        return snapshotService.getByTemplateAndVersion(templateId, version);
    }
}
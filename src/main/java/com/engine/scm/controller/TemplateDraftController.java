package com.engine.scm.controller;

import com.engine.scm.domain.TemplateDraft;
import com.engine.scm.dto.TemplateDraftStatus;
import com.engine.scm.service.TemplateDraftService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/templates/drafts")
@Tag(name = "Template Draft API", description = "模板草稿管理接口")
@RequiredArgsConstructor
public class TemplateDraftController {

    private final TemplateDraftService templateDraftService;

    /* ================= 创建 Draft ================= */

    @PostMapping
    @Operation(summary = "创建草稿", description = "创建一个新的模板草稿")
    public TemplateDraft create(@RequestBody TemplateDraft draft) {
        return templateDraftService.create(draft);
    }

    /* ================= 查询 Draft ================= */

    @GetMapping("/{id}")
    @Operation(summary = "获取草稿", description = "根据 ID 获取模板草稿详情")
    public TemplateDraft get(@Parameter(description = "草稿 ID", required = true) @PathVariable String id) {
        return templateDraftService.getById(id);
    }

    @GetMapping
    @Operation(summary = "查询草稿列表", description = "根据业务编码和状态筛选草稿")
    public List<TemplateDraft> list(
            @Parameter(description = "业务维度编码") @RequestParam(required = false) String bizCode,
            @Parameter(description = "草稿状态") @RequestParam(required = false) TemplateDraftStatus status
    ) {
        return templateDraftService.list(bizCode,status);
    }

    /* ================= 更新 Draft ================= */

    @PutMapping("/{id}")
    @Operation(summary = "更新草稿", description = "更新模板草稿内容")
    public TemplateDraft update(
            @Parameter(description = "草稿 ID", required = true) @PathVariable String id,
            @RequestBody TemplateDraft update
    ) {
        return templateDraftService.update(id,update);
    }

    /* ================= 锁定 Draft ================= */

    @PostMapping("/{id}/lock")
    @Operation(summary = "锁定草稿", description = "锁定草稿以防止修改，准备发布")
    public TemplateDraft lock(@Parameter(description = "草稿 ID", required = true) @PathVariable String id) {
        return templateDraftService.lock(id);
    }

    /* ================= 解锁 Draft ================= */

    @PostMapping("/{id}/unlock")
    @Operation(summary = "解锁草稿", description = "将锁定的草稿解锁，允许再次编辑")
    public TemplateDraft unlock(@Parameter(description = "草稿 ID", required = true) @PathVariable String id) {
        return templateDraftService.unlock(id);
    }

    /* ================= 删除 Draft ================= */

    @DeleteMapping("/{id}")
    @Operation(summary = "删除草稿", description = "删除指定的模板草稿")
    public void delete(@Parameter(description = "草稿 ID", required = true) @PathVariable String id) {
        templateDraftService.delete(id);
    }
}
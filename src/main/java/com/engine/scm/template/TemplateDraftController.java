package com.engine.scm.template;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/templates/drafts")
@RequiredArgsConstructor
public class TemplateDraftController {

    private final TemplateDraftService templateDraftService;

    /* ================= 创建 Draft ================= */

    @PostMapping
    public TemplateDraft create(@RequestBody TemplateDraft draft) {
        return templateDraftService.create(draft);
    }

    /* ================= 查询 Draft ================= */

    @GetMapping("/{id}")
    public TemplateDraft get(@PathVariable String id) {
        return templateDraftService.getById(id);
    }

    @GetMapping
    public List<TemplateDraft> list(
            @RequestParam(required = false) String bizCode,
            @RequestParam(required = false) TemplateDraftStatus status
    ) {
        return templateDraftService.list(bizCode,status);
    }

    /* ================= 更新 Draft ================= */

    @PutMapping("/{id}")
    public TemplateDraft update(
            @PathVariable String id,
            @RequestBody TemplateDraft update
    ) {
        return templateDraftService.update(id,update);
    }

    /* ================= 锁定 Draft ================= */

    @PostMapping("/{id}/lock")
    public TemplateDraft lock(@PathVariable String id) {
        return templateDraftService.lock(id);
    }

    /* ================= 删除 Draft ================= */

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        templateDraftService.delete(id);
    }
}

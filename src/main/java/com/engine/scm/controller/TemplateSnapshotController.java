package com.engine.scm.controller;

import com.engine.scm.domain.TemplateSnapshot;
import com.engine.scm.service.TemplateSnapshotService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/templates/snapshots")
@RequiredArgsConstructor
public class  TemplateSnapshotController {

    private final TemplateSnapshotService snapshotService;

    @GetMapping("/{id}")
    public TemplateSnapshot get(@PathVariable String id) {
        return snapshotService.getById(id);
    }

    @GetMapping
    public List<TemplateSnapshot> listByTemplate(
            @RequestParam String templateId
    ) {
        return snapshotService.listByTemplate(templateId);
    }

    @GetMapping("/latest")
    public TemplateSnapshot latest(
            @RequestParam String templateId
    ) {
        return snapshotService.getLatest(templateId);
    }

    @GetMapping("/by-version")
    public TemplateSnapshot byVersion(
            @RequestParam String templateId,
            @RequestParam String version
    ) {
        return snapshotService.getByTemplateAndVersion(templateId, version);
    }
}

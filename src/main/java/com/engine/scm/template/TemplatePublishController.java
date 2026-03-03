package com.engine.scm.template;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/templates/publish")
@RequiredArgsConstructor
public class TemplatePublishController {

    private final TemplatePublishService publishService;

    @PostMapping("/{draftId}")
    public TemplateSnapshot publish(
            @PathVariable String draftId,
            @RequestParam String version,
            @RequestBody(required = false)
            Map<String, Object> overrides
    ) {
        return publishService.publish(
                draftId,
                version,
                overrides,
                RuntimeContexts.system()
        );
    }
}

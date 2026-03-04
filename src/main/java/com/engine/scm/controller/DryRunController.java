package com.engine.scm.controller;

import com.engine.scm.dto.DryRunRequest;
import com.engine.scm.dto.DryRunResult;
import com.engine.scm.service.DryRunService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/template/dry-run")
public class DryRunController {

    @Autowired
    private DryRunService dryRunService;

    @PostMapping
    public DryRunResult dryRun(@RequestBody DryRunRequest request) {
        return dryRunService.dryRun(request);
    }
}

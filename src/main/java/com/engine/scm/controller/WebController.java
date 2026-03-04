package com.engine.scm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/templates")
public class WebController {

    @GetMapping("/drafts")
    public String drafts() {
        return "draft-list";
    }

    @GetMapping("/snapshots")
    public String snapshots() {
        return "snapshot-list";
    }

    @GetMapping("/publish")
    public String publish() {
        return "publish-list";
    }
    
    @GetMapping("/dry-run")
    public String dryRun() {
        return "dry-run";
    }
}

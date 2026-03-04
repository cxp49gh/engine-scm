package com.engine.scm.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;

@Service
public class RiskAssessService {

    public String assess(JsonNode diff) {
        int changes = diff.size();
        if (changes == 0) return "NONE";
        if (changes < 5) return "LOW";
        if (changes < 15) return "MEDIUM";
        return "HIGH";
    }
}

package com.engine.scm.domain;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Map;
@Document("template_snapshot")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TemplateSnapshot {
    @Id
    String id;

    String templateId;     // draftId
    String version;        // v1 / 2024.01.19 / semver

    String templateContent;
    Map<String, Object> defaultParams;

    String runtimeParamDefRef;

    JsonNode diffFromPrev; // 审计 & 变更说明
    String riskLevel;      // LOW / MEDIUM / HIGH

    Instant publishedAt;

}


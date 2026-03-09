package com.engine.scm.domain;

import com.engine.scm.dto.ParamMeta;
import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Document("template_snapshot")
@CompoundIndexes({
    @CompoundIndex(name = "template_version_unique", def = "{'templateId': 1, 'version': 1}", unique = true)
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "模板快照")
public class TemplateSnapshot {

    @Id
    @Schema(description = "快照 ID")
    String id;

    @Schema(description = "模板/草稿 ID", example = "64f8a2b3c9e1a1234567890a")
    @Indexed
    String templateId;

    @Schema(description = "版本号", example = "v1")
    String version;

    @Schema(description = "模板内容 (Freemarker)")
    String templateContent;

    @Schema(description = "运行时参数定义")
    List<ParamMeta> params;

    @Schema(description = "与上一版本的 Diff")
    JsonNode diffFromPrev;

    @Schema(description = "风险等级: LOW / MEDIUM / HIGH", example = "LOW")
    String riskLevel;

    @Schema(description = "发布时间")
    Instant publishedAt;
}
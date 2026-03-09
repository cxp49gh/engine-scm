package com.engine.scm.domain;

import com.engine.scm.dto.ParamMeta;
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

@Document(collection = "template_draft")
@CompoundIndexes({
    @CompoundIndex(name = "biz_engine_link_idx", def = "{'bizCode': 1, 'engineCode': 1, 'linkCode': 1}")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "模板草稿")
public class TemplateDraft {

    @Id
    @Schema(description = "草稿 ID")
    private String id;

    @Schema(description = "业务维度", example = "payment")
    @Indexed
    private String bizCode;

    @Schema(description = "引擎维度", example = "order-engine")
    @Indexed
    private String engineCode;

    @Schema(description = "执行环节", example = "pre-check")
    @Indexed
    private String linkCode;

    @Schema(description = "模板名称", example = "订单预处理配置")
    private String name;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "当前草稿指向的版本号", example = "v1")
    private String currentVersion;

    @Schema(description = "Freemarker 原始模板")
    private String templateContent;

    @Schema(description = "运行时参数定义")
    private List<ParamMeta> params;

    @Schema(description = "状态: DRAFT / LOCKED / DEPRECATED", example = "DRAFT")
    @Indexed
    private String status;

    @Schema(description = "创建时间")
    @Indexed
    private Instant createdAt;

    @Schema(description = "更新时间")
    @Indexed
    private Instant updatedAt;
}
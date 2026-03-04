package com.engine.scm.domain;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Map;
@Document(collection = "template_draft")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TemplateDraft {

    @Id
    private String id;

    /** 业务维度 */
    @Indexed
    private String bizCode;

    /** 引擎维度 */
    @Indexed
    private String engineCode;

    /** 执行环节 */
    @Indexed
    private String linkCode;

    /** 模板名称 */
    private String name;

    private String description;

    /** 当前草稿指向的版本号（仅逻辑概念） */
    private String currentVersion;

    /** Freemarker 原始模板（JSON 结构） */
    private JsonNode templateContent;

    /** 默认参数（用于 ParamMerge） */
    private Map<String, Object> defaultParams;

    /** 运行时参数定义引用（ParamMeta 集） */
    private String runtimeParamDefRef;

    /** DRAFT / LOCKED / DEPRECATED */
    @Indexed
    private String status;

    @Indexed
    private Instant createdAt;

    @Indexed
    private Instant updatedAt;
}

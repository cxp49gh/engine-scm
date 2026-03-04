package com.engine.scm.domain;

import com.engine.scm.dto.ParamMeta;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Document("runtime_param_def")
@Data
public class RuntimeParamDefinition {

    @Id
    private String id;  // runtimeParamDefRef

    private String engineCode;
    private String version;

    /** 参数定义集合 */
    private List<ParamMeta> params;

    private Instant createdAt;
}

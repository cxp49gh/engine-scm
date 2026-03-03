package com.engine.scm.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "SCM 配置管理服务",
        version = "v1",
        description = "Engine Template / Runtime Param / Dry-Run API"
    )
)
public class OpenApiConfig {
}

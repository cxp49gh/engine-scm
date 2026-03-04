package com.engine.scm.util;

import com.engine.scm.exception.BizException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JsonSchemaValidator {

    private final ObjectMapper objectMapper;
    private final JsonSchema schema;

    public JsonSchemaValidator(ObjectMapper objectMapper) throws IOException {
        this.objectMapper = objectMapper;
        JsonNode schemaNode = objectMapper.readTree(
                new ClassPathResource(
                        "schema/engine-template-v1.json"
                ).getInputStream()
        );
        this.schema = JsonSchemaFactory
                .getInstance(SpecVersion.VersionFlag.V7)
                .getSchema(schemaNode);
    }

    public void validate(JsonNode json) {
        Set<ValidationMessage> errors = schema.validate(json);
        if (!errors.isEmpty()) {
            throw BizException.invalid(
                    "JSON schema validation failed",
                    errors.stream()
                            .map(ValidationMessage::getMessage)
                            .collect(Collectors.toList())
            );
        }
    }
}

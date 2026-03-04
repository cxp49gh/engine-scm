package com.engine.scm.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.bson.Document;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.util.Arrays;

/**
 * MongoDB 配置
 */
@Configuration
public class MongoConfig {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Bean
    public MongoCustomConversions customConversions() {
        return new MongoCustomConversions(Arrays.asList(
                new JsonNodeToStringConverter(),
                new StringToJsonNodeConverter(),
                new DocumentToJsonNodeConverter()
        ));
    }

    /**
     * JsonNode 转 String 的转换器（用于写入 MongoDB）
     */
    private class JsonNodeToStringConverter implements Converter<JsonNode, String> {

        @Override
        public String convert(JsonNode source) {
            try {
                return objectMapper.writeValueAsString(source);
            } catch (JsonProcessingException e) {
                return null;
            }
        }
    }

    /**
     * String 转 JsonNode 的转换器（用于从 MongoDB 读取）
     */
    private class StringToJsonNodeConverter implements Converter<String, JsonNode> {

        @Override
        public JsonNode convert(String source) {
            try {
                return objectMapper.readTree(source);
            } catch (JsonProcessingException e) {
                return null;
            }
        }
    }

    /**
     * Document 转 JsonNode 的转换器（用于从 MongoDB 读取 JsonNode 类型的字段）
     */
    private class DocumentToJsonNodeConverter implements Converter<Document, JsonNode> {

        @Override
        public JsonNode convert(Document source) {
            return objectMapper.valueToTree(source);
        }
    }

}
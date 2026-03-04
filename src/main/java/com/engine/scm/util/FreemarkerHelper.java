package com.engine.scm.util;

import com.engine.scm.exception.BizException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.StringWriter;
import java.util.Map;
@Component
@RequiredArgsConstructor
public class FreemarkerHelper {

    private final Configuration freemarkerConfig;
    private final ObjectMapper objectMapper;

    /**
     * 将 JSON 模板 + 参数 渲染为 JSON 结果
     */
    public JsonNode renderJson(
            JsonNode templateContent,
            Map<String, Object> params
    ) {
        try {
            StringWriter out = new StringWriter();

            Template template = new Template(
                    "template-" + System.nanoTime(),
                    templateContent.toString(),
                    freemarkerConfig
            );

            template.process(params, out);

            return objectMapper.readTree(out.toString());

        } catch (Exception e) {
            throw BizException.invalid(
                    "Freemarker render failed: " + e.getMessage(), e
            );
        }
    }
}

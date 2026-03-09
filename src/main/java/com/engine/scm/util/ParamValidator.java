package com.engine.scm.util;

import com.engine.scm.dto.ParamMeta;
import com.engine.scm.dto.ParamType;
import com.engine.scm.exception.BizException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Service
public class ParamValidator {

    public void validate(
            Map<String, Object> params,
            List<ParamMeta> paramMetaList) {

        if (paramMetaList == null || paramMetaList.isEmpty()) {
            return;
        }

        Map<String, ParamMeta> metaMap =
            paramMetaList.stream()
               .collect(Collectors.toMap(ParamMeta::getName, p -> p));

        // 1. 必填校验
        for (ParamMeta meta : metaMap.values()) {
            if (meta.isRequired() && !params.containsKey(meta.getName())) {
                throw BizException.invalid(
                    "Missing required param: " + meta.getName());
            }
        }

        // 2. 参数合法性校验
        for (Map.Entry<String, Object> e : params.entrySet()) {
            ParamMeta meta = metaMap.get(e.getKey());
            if (meta == null) {
                throw BizException.invalid("Unknown param: " + e.getKey());
            }
            validateSingle(e.getValue(), meta);
        }
    }

    private void validateSingle(Object value, ParamMeta meta) {

        if (value == null) {
            return;
        }

        ParamType type = meta.getType();

        switch (type) {

            case INT: {
                int v;
                try {
                    v = Integer.parseInt(value.toString());
                } catch (NumberFormatException e) {
                    throw BizException.invalid(
                            meta.getName() + " must be INT, actual=" + value);
                }

                if (meta.getMin() != null && v < meta.getMin().intValue()) {
                    throw BizException.invalid(meta.getName() + " too small");
                }
                if (meta.getMax() != null && v > meta.getMax().intValue()) {
                    throw BizException.invalid(meta.getName() + " too large");
                }
                break;
            }

            case BOOLEAN: {
                if (!"true".equalsIgnoreCase(value.toString())
                        && !"false".equalsIgnoreCase(value.toString())) {
                    throw BizException.invalid(
                            meta.getName() + " must be BOOLEAN");
                }
                break;
            }

            case STRING_LIST: {
                String s = value.toString();
                if (meta.getPattern() != null && !s.matches(meta.getPattern())) {
                    throw BizException.invalid(
                            "Invalid format: " + meta.getName());
                }
                break;
            }

            case STRING: {
                if (meta.getPattern() != null &&
                        !value.toString().matches(meta.getPattern())) {
                    throw BizException.invalid(
                            "Invalid format: " + meta.getName());
                }
                break;
            }

            default:
                // 其他类型（OBJECT / ARRAY）可延后处理
                break;
        }

        // 枚举校验（所有类型通用）
        if (meta.getEnumValues() != null
                && !meta.getEnumValues().isEmpty()
                && !meta.getEnumValues().contains(value)) {

            throw BizException.invalid(
                    "Invalid enum value for " + meta.getName()
                            + ", allowed=" + meta.getEnumValues());
        }
    }

}

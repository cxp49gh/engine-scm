package com.engine.scm.service;

import com.engine.scm.domain.RuntimeParamDefinition;
import com.engine.scm.dto.ParamMeta;
import com.engine.scm.exception.BizException;
import com.engine.scm.repository.RuntimeParamDefRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Service
public class RuntimeParamDefServiceImpl
        implements RuntimeParamDefService {

    @Autowired
    private RuntimeParamDefRepository repository;

    @Override
    public RuntimeParamDefinition get(String ref) {

        if (ref == null || ref.trim().isEmpty()) {
            throw BizException.invalid("runtimeParamDefRef is required");
        }

        return repository.findById(ref)
                .orElseThrow(() ->
                        BizException.invalid(
                                "RuntimeParamDefinition not found: " + ref));
    }

    @Override
    public boolean exists(String ref) {
        return ref != null && repository.existsById(ref);
    }

    @Override
    public RuntimeParamDefinition save(RuntimeParamDefinition def) {

        if (def.getId() == null || def.getId().trim().isEmpty()) {
            throw BizException.invalid("ParamDef id (ref) is required");
        }

        if (def.getParams() == null || def.getParams().isEmpty()) {
            throw BizException.invalid("ParamDef params cannot be empty");
        }

        // 基础合法性校验（不是值校验，是规则校验）
        validateMeta(def);

        def.setCreatedAt(Instant.now());
        return repository.save(def);
    }

    private void validateMeta(RuntimeParamDefinition def) {

        Set<String> names = new HashSet<>();

        for (ParamMeta meta : def.getParams()) {

            if (meta.getName() == null || meta.getName().trim().isEmpty()) {
                throw BizException.invalid("ParamMeta name is required");
            }

            if (!names.add(meta.getName())) {
                throw BizException.invalid(
                        "Duplicate ParamMeta name: " + meta.getName());
            }

            if (meta.getType() == null) {
                throw BizException.invalid(
                        "ParamMeta type is required: " + meta.getName());
            }
        }
    }
}

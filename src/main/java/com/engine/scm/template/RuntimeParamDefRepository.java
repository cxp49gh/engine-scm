package com.engine.scm.template;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RuntimeParamDefRepository
        extends MongoRepository<RuntimeParamDefinition, String> {

    Optional<RuntimeParamDefinition> findById(String id);
}

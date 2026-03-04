package com.engine.scm.repository;

import com.engine.scm.domain.RuntimeParamDefinition;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RuntimeParamDefRepository
        extends MongoRepository<RuntimeParamDefinition, String> {

    Optional<RuntimeParamDefinition> findById(String id);
}

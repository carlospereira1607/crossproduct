package com.marketplace.crossproduct.core.port.db;

import com.marketplace.crossproduct.core.model.AttributeDefinitionSpecification;

import java.util.Optional;

public interface AttributeDefinitionSpecificationPortRepository {

    Optional<AttributeDefinitionSpecification> findById(Long id);

}

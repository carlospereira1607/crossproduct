package com.marketplace.crossproduct.core.port.db;

import com.marketplace.crossproduct.core.model.AttributeDefinition;

import java.util.Optional;

public interface AttributeDefinitionPortRepository {

    AttributeDefinition save(final AttributeDefinition attributeDefinition);

    Optional<AttributeDefinition> findByPortalIdAndName(final Long portalId, final String name);

    Optional<AttributeDefinition> findById(final Long id);

}

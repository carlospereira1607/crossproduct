package com.marketplace.crossproduct.core.port.db;

import com.marketplace.crossproduct.core.model.AttributeValue;

import java.util.Optional;

public interface AttributeValuePortRepository {

    Optional<AttributeValue> findByPortalProductDefinition(Long portalId, Long productId, Long definitionId);

    AttributeValue save(AttributeValue entry);

}

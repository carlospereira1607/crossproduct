package com.marketplace.crossproduct.core.port.db;

import com.marketplace.crossproduct.core.model.AttributeValue;

import java.util.Optional;
import java.util.Set;

public interface AttributeValuePortRepository {

    Optional<AttributeValue> findByPortalProductDefinition(Long portalId, Long productId, Long definitionId);

    AttributeValue save(AttributeValue entry);

    Set<AttributeValue> findByProductIdAndPortalId(Long productId, Long portalId);

    Set<AttributeValue> findByProductId(Long productId);

    void updateValueAndIsStandard(Long portalId, Long productId, Long definitionId, String value, Boolean isStandard);

}

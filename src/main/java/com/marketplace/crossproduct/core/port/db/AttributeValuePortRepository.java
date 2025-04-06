package com.marketplace.crossproduct.core.port.db;

import com.marketplace.crossproduct.core.model.AttributeValue;

import java.util.Optional;

public interface AttributeValuePortRepository {

    Optional<AttributeValue> findById(final Long id);

    AttributeValue save(final AttributeValue attributeValue);

}

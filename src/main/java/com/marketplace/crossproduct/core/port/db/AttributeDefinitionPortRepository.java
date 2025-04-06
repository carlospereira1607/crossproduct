package com.marketplace.crossproduct.core.port.db;

import com.marketplace.crossproduct.core.model.AttributeDefinition;

import java.util.Optional;
import java.util.Set;

public interface AttributeDefinitionPortRepository {

    AttributeDefinition save(final AttributeDefinition attributeDefinition);

    Optional<AttributeDefinition> findByNameAndTypeAndSpecificationIdAndSelectableOptions(final String name, final String type,
                                                                                          final Long specificationId,
                                                                                          final Set<String> selectableOptions);

    Optional<AttributeDefinition> findById(final Long id);

}

package com.marketplace.crossproduct.core.port.db;

import com.marketplace.crossproduct.core.model.AttributeDefinition;
import com.marketplace.crossproduct.core.model.AttributeDefinitionSpecificationType;
import com.marketplace.crossproduct.core.model.AttributeDefinitionType;

import java.util.Optional;
import java.util.Set;

public interface AttributeDefinitionPortRepository {

    AttributeDefinition save(final AttributeDefinition attributeDefinition);

    Optional<AttributeDefinition> findByNameAndTypeAndSpecificationIdAndSelectableOptions(final String name, final AttributeDefinitionType definitionType,
                                                                                          final AttributeDefinitionSpecificationType specificationType,
                                                                                          final String value,
                                                                                          final Set<String> selectableOptions);

    Optional<AttributeDefinition> findById(final Long id);

}

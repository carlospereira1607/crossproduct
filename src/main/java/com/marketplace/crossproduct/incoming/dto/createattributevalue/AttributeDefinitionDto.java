package com.marketplace.crossproduct.incoming.dto.createattributevalue;

import com.marketplace.crossproduct.core.model.AttributeDefinitionSpecificationType;
import com.marketplace.crossproduct.core.model.AttributeDefinitionType;

import java.util.Set;

public record AttributeDefinitionDto(Long id, String name, AttributeDefinitionType definitionType,
                                     AttributeDefinitionSpecificationType specificationType,
                                     String specificationValue, Set<String> selectableOptions) {
}

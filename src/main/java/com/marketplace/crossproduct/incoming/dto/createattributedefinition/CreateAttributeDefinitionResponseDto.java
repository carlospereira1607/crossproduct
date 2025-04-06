package com.marketplace.crossproduct.incoming.dto.createattributedefinition;

import com.marketplace.crossproduct.core.model.AttributeDefinitionSpecificationType;
import com.marketplace.crossproduct.core.model.AttributeDefinitionType;

import java.util.Set;

public record CreateAttributeDefinitionResponseDto(Long id, String name, AttributeDefinitionType definitionType,
                                                   AttributeDefinitionSpecificationType specificationType,
                                                   String specificationValue, Set<String> selectableOptions) {
}

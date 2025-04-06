package com.marketplace.crossproduct.incoming.dto.createattributedefinition;

import com.marketplace.crossproduct.core.model.AttributeDefinitionType;

import java.util.Set;

public record CreateAttributeDefinitionResponseDto(Long id, String name, AttributeDefinitionType type,
                                                   Long specificationId, Set<String> selectableOptions) {
}

package com.marketplace.crossproduct.incoming.dto.createattributedefinition;

import java.util.Set;

public record CreateAttributeDefinitionRequestDto(String name, String definitionType, String specificationType,
                                                  String specificationValue, Set<String> selectableOptions) {
}

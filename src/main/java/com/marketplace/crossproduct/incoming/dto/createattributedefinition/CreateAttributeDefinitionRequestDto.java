package com.marketplace.crossproduct.incoming.dto.createattributedefinition;

import java.util.Set;

public record CreateAttributeDefinitionRequestDto(Long portalId, String name, String definitionType, String specificationType,
                                                  String value, Set<String> selectableOptions) {
}

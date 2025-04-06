package com.marketplace.crossproduct.incoming.dto.createattributedefinition;

import java.util.Set;

public record CreateAttributeDefinitionRequestDto(Long portalId, String name, String type,
                                                  Long specificationId, Set<String> selectableOptions) {
}

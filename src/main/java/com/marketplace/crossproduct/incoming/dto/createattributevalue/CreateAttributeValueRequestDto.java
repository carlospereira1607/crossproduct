package com.marketplace.crossproduct.incoming.dto.createattributevalue;

public record CreateAttributeValueRequestDto(Long attributeDefinitionId, String value, boolean isStandard) {
}

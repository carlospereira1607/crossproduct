package com.marketplace.crossproduct.incoming.dto.updateattributevalue;

public record UpdateAttributeValueRequestDto(Long attributeDefinitionId, String value, boolean isStandard) {
}

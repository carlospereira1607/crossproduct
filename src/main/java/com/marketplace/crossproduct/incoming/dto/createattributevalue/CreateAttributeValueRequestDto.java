package com.marketplace.crossproduct.incoming.dto.createattributevalue;

public record CreateAttributeValueRequestDto(
        Long attributeDefinitionId,
        Long portalId,
        Long productId,
        String value,
        boolean isStandard) {
}

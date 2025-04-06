package com.marketplace.crossproduct.incoming.dto.createattributevalue;

public record CreateAttributeValueRequestDto(
        Long definitionId,
        Long portalId,
        Long productId,
        String value,
        boolean isStandard) {
}

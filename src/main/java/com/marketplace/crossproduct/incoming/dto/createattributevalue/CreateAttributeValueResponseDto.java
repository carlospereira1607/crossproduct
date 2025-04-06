package com.marketplace.crossproduct.incoming.dto.createattributevalue;

public record CreateAttributeValueResponseDto(String value, boolean isStandard, PortalDto portal, ProductDto product, AttributeDefinitionDto definition) {
}

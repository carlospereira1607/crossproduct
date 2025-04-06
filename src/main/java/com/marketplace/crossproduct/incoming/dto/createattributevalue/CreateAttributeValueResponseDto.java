package com.marketplace.crossproduct.incoming.dto.createattributevalue;

import com.marketplace.crossproduct.core.model.AttributeDefinition;

public record CreateAttributeValueResponseDto(Long id, String value, boolean isStandard, AttributeDefinition definition) {
}

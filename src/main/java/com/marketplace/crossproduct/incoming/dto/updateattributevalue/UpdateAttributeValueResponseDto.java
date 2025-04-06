package com.marketplace.crossproduct.incoming.dto.updateattributevalue;

import com.marketplace.crossproduct.core.model.AttributeDefinition;

public record UpdateAttributeValueResponseDto(Long id, String value, boolean isStandard, AttributeDefinition definition) {
}

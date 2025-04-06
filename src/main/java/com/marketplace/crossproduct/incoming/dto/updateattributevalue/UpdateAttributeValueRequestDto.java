package com.marketplace.crossproduct.incoming.dto.updateattributevalue;

public record UpdateAttributeValueRequestDto(Long definitionId,
                                             Long portalId,
                                             Long productId,
                                             String value,
                                             boolean isStandard) {
}

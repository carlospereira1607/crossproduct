package com.marketplace.crossproduct.incoming.dto.getproductdetails;

public record AttributeValueDetailsDto(Long valueId, String value, boolean isStandard, AttributeDefinitionDetailsDto definition) {
}

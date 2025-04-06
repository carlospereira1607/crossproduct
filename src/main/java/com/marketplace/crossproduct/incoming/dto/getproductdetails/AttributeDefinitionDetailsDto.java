package com.marketplace.crossproduct.incoming.dto.getproductdetails;

import java.util.Set;

public record AttributeDefinitionDetailsDto(Long id, String name, String type, AttributeSpecificationDto specification, Set<String> selectableOptions) {
}

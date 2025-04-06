package com.marketplace.crossproduct.incoming.dto.getproductdetails;

import com.marketplace.crossproduct.core.model.AttributeDefinitionSpecificationType;
import com.marketplace.crossproduct.core.model.AttributeDefinitionType;

import java.util.Set;

public record AttributeDefinitionDetailsDto(Long id, String name, AttributeDefinitionType definitionType,
                                            AttributeDefinitionSpecificationType specificationType, String value, Set<String> selectableOptions) {
}

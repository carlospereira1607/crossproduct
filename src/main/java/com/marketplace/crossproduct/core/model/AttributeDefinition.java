package com.marketplace.crossproduct.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AttributeDefinition {

    private Long id;

    private String name;

    private AttributeDefinitionType definitionType;

    private AttributeDefinitionSpecificationType specificationType;

    private String specificationValue;

    private Set<String> selectableOptions;

    private Set<AttributeValue> values;

}

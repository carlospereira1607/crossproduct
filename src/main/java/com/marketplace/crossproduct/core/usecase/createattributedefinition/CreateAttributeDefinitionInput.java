package com.marketplace.crossproduct.core.usecase.createattributedefinition;

import com.marketplace.crossproduct.core.usecase.UseCaseInput;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
@Builder
public class CreateAttributeDefinitionInput extends UseCaseInput {
    private String name;
    private String definitionType;
    private String specificationType;
    private String specificationValue;
    private Set<String> selectableOptions;
}

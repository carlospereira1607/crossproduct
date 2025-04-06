package com.marketplace.crossproduct.core.usecase.createattributedefinition;

import com.marketplace.crossproduct.core.model.AttributeDefinitionSpecificationType;
import com.marketplace.crossproduct.core.model.AttributeDefinitionType;
import com.marketplace.crossproduct.core.usecase.UseCaseOutput;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
@Builder
public class CreateAttributeDefinitionOutput extends UseCaseOutput {
    private Long id;
    private String name;
    private AttributeDefinitionType definitionType;
    private AttributeDefinitionSpecificationType specificationType;
    private String value;
    private Set<String> selectableOptions;
}

package com.marketplace.crossproduct.core.usecase.createattributedefinition;

import com.marketplace.crossproduct.core.usecase.UseCaseInput;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
@Builder
public class CreateAttributeDefinitionInput extends UseCaseInput {
    private Long portalId;
    private String name;
    private String type;
    private Long specificationId;
    private Set<String> selectableOptions;
}

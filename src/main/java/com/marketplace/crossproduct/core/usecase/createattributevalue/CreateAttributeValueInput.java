package com.marketplace.crossproduct.core.usecase.createattributevalue;

import com.marketplace.crossproduct.core.usecase.UseCaseInput;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateAttributeValueInput extends UseCaseInput {
    private Long attributeDefinitionId;
    private Long portalId;
    private Long productId;
    private String value;
    private boolean isStandard;
}

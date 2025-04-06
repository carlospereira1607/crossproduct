package com.marketplace.crossproduct.core.usecase.updateattributevalue;

import com.marketplace.crossproduct.core.usecase.UseCaseInput;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateAttributeValueInput extends UseCaseInput {
    private Long attributeValueId;
    private String value;
    private Boolean isStandard;
    private Long attributeDefinitionId;
}

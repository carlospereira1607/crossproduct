package com.marketplace.crossproduct.core.usecase.updateattributevalue;

import com.marketplace.crossproduct.core.usecase.UseCaseInput;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UpdateAttributeValueInput extends UseCaseInput {
    private String value;
    private Boolean isStandard;
    private Long definitionId;
    private Long portalId;
    private Long productId;
}

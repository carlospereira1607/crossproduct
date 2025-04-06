package com.marketplace.crossproduct.core.usecase.updateattributevalue;

import com.marketplace.crossproduct.core.model.AttributeDefinition;
import com.marketplace.crossproduct.core.usecase.UseCaseOutput;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateAttributeValueOutput extends UseCaseOutput {
    private Long id;
    private String value;
    private boolean isStandard;
    private AttributeDefinition definition;
}

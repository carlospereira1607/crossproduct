package com.marketplace.crossproduct.core.usecase.updateattributevalue;

import com.marketplace.crossproduct.core.model.AttributeDefinition;
import com.marketplace.crossproduct.core.model.Portal;
import com.marketplace.crossproduct.core.model.Product;
import com.marketplace.crossproduct.core.usecase.UseCaseOutput;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateAttributeValueOutput extends UseCaseOutput {
    private String value;
    private boolean isStandard;
    private Portal portal;
    private Product product;
    private AttributeDefinition definition;
}

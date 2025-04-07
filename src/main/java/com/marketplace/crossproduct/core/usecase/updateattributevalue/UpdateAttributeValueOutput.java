package com.marketplace.crossproduct.core.usecase.updateattributevalue;

import com.marketplace.crossproduct.core.usecase.UseCaseOutput;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateAttributeValueOutput extends UseCaseOutput {
    private int updatedEntries;
}

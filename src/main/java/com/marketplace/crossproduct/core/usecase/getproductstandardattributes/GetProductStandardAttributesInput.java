package com.marketplace.crossproduct.core.usecase.getproductstandardattributes;

import com.marketplace.crossproduct.core.usecase.UseCaseInput;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetProductStandardAttributesInput extends UseCaseInput {
    private Long productId;
}

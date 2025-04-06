package com.marketplace.crossproduct.core.usecase.getproductdetails;

import com.marketplace.crossproduct.core.usecase.UseCaseInput;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetProductDetailsInput extends UseCaseInput {
    private Long productId;
}

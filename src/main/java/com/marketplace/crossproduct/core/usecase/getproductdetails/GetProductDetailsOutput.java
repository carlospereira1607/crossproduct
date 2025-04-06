package com.marketplace.crossproduct.core.usecase.getproductdetails;

import com.marketplace.crossproduct.core.model.Product;
import com.marketplace.crossproduct.core.usecase.UseCaseOutput;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class GetProductDetailsOutput extends UseCaseOutput {
    private Product product;
}

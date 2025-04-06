package com.marketplace.crossproduct.core.usecase.getproductsbyportal;

import com.marketplace.crossproduct.core.usecase.UseCaseOutput;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Builder
@Getter
@AllArgsConstructor
public class GetProductsByPortalOutput extends UseCaseOutput {
    private Set<ProductIdAndName> products;
}

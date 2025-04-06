package com.marketplace.crossproduct.core.usecase.getproductsbyportal;

import com.marketplace.crossproduct.core.usecase.UseCaseInput;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetProductsByPortalInput extends UseCaseInput {
    private Long portalId;
}

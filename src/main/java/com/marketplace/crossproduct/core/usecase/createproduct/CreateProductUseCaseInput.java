package com.marketplace.crossproduct.core.usecase.createproduct;

import com.marketplace.crossproduct.core.usecase.UseCaseInput;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateProductUseCaseInput extends UseCaseInput {
    private String name;
}

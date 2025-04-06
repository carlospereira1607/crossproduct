package com.marketplace.crossproduct.core.usecase.createproduct;

import com.marketplace.crossproduct.core.usecase.UseCaseOutput;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateProductUseCaseOutput extends UseCaseOutput {
    private Long id;
    private String name;
}

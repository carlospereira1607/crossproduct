package com.marketplace.crossproduct.core.usecase.createportal;

import com.marketplace.crossproduct.core.usecase.UseCaseInput;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreatePortalUseCaseInput extends UseCaseInput {
    private String name;
}

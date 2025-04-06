package com.marketplace.crossproduct.core.usecase.createportal;

import com.marketplace.crossproduct.core.usecase.UseCaseOutput;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreatePortalUseCaseOutput extends UseCaseOutput {
    private Long id;
    private String name;
}

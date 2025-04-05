package com.marketplace.crossproduct.core.usecase.auth;

import com.marketplace.crossproduct.core.usecase.UseCaseOutput;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AuthenticateUserOutput extends UseCaseOutput {
    private String token;
}

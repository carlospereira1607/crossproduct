package com.marketplace.crossproduct.core.usecase.auth;

import com.marketplace.crossproduct.core.usecase.UseCaseInput;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AuthenticateUserInput extends UseCaseInput {

    private String username;
    private String password;

}

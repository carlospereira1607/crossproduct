package com.marketplace.crossproduct.core.usecase.createuser;

import com.marketplace.crossproduct.core.usecase.UseCaseInput;
import com.marketplace.crossproduct.security.Role;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateUserInput extends UseCaseInput {

    private String username;
    private String password;
    private Role role;
    private Long portalId;

}

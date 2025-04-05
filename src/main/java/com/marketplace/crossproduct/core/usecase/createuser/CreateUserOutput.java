package com.marketplace.crossproduct.core.usecase.createuser;

import com.marketplace.crossproduct.core.usecase.UseCaseOutput;
import com.marketplace.crossproduct.security.Role;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreateUserOutput extends UseCaseOutput {
    private Long id;
    private String username;
    private String password;
    private Role role;
    private Long portalId;
}

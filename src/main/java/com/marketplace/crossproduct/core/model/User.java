package com.marketplace.crossproduct.core.model;

import com.marketplace.crossproduct.security.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class User {
    private Long id;
    private String username;
    private String password;
    private Role role;
}

package com.marketplace.crossproduct.incoming.dto.createuser;

public record CreateUserRequestDto(String username, String password, String role, Long portalId) {}

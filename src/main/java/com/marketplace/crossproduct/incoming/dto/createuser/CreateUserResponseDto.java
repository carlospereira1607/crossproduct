package com.marketplace.crossproduct.incoming.dto.createuser;

public record CreateUserResponseDto(Long id, String username, String password, String role, Long portalId) {}

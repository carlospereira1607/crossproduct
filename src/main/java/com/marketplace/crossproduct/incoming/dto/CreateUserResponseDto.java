package com.marketplace.crossproduct.incoming.dto;

public record CreateUserResponseDto(Long id, String username, String password, String role, Long portalId) {}

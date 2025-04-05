package com.marketplace.crossproduct.incoming.dto;

public record CreateUserRequestDto(String username, String password, String role, Long portalId) {}

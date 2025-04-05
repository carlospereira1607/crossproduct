package com.marketplace.crossproduct.core.port.db;

import com.marketplace.crossproduct.core.model.User;

import java.util.Optional;

public interface UserPortRepository {

    Optional<User> findByUsername(final String username);

}

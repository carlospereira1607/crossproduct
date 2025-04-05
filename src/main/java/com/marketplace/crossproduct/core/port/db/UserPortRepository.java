package com.marketplace.crossproduct.core.port.db;

import com.marketplace.crossproduct.core.model.User;

import java.util.Optional;

public interface UserPortRepository {

    User save(final User user);

    Optional<User> findByUsername(final String username);

}

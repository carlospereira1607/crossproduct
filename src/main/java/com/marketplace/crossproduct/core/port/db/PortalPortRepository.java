package com.marketplace.crossproduct.core.port.db;

import com.marketplace.crossproduct.core.model.Portal;

import java.util.Optional;

public interface PortalPortRepository {

    Optional<Portal> findById(final Long id);

}

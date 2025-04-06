package com.marketplace.crossproduct.core.port.db;

import com.marketplace.crossproduct.core.model.Product;

import java.util.Optional;
import java.util.Set;

public interface ProductPortRepository {

    Optional<Product> getById(final Long id);

    Set<Product> getByPortalId(final Long portalId);

}

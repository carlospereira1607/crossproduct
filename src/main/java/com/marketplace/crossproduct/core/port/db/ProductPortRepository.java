package com.marketplace.crossproduct.core.port.db;

import com.marketplace.crossproduct.core.model.Product;

import java.util.Set;

public interface ProductPortRepository {

    Set<Product> getByPortalId(final Long portalId);

}

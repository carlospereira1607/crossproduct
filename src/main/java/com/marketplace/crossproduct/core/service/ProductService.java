package com.marketplace.crossproduct.core.service;

import com.marketplace.crossproduct.core.model.Product;
import com.marketplace.crossproduct.core.port.db.ProductPortRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductPortRepository productPortRepository;

    public Set<Product> getByPortalId(final Long portalId) {
        return productPortRepository.getByPortalId(portalId);
    }
}

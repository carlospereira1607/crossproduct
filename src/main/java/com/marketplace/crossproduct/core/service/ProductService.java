package com.marketplace.crossproduct.core.service;

import com.marketplace.crossproduct.core.model.Product;
import com.marketplace.crossproduct.core.port.db.ProductPortRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductPortRepository productPortRepository;

    public Optional<Product> findById(final Long id) {
        return productPortRepository.getById(id);
    }

    public Set<Product> findByPortalId(final Long portalId) {
        return productPortRepository.getByPortalId(portalId);
    }
}

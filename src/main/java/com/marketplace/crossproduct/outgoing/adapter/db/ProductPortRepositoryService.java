package com.marketplace.crossproduct.outgoing.adapter.db;

import com.marketplace.crossproduct.core.model.Product;
import com.marketplace.crossproduct.core.port.db.ProductPortRepository;
import com.marketplace.crossproduct.outgoing.adapter.db.repository.ProductEntityRepository;
import com.marketplace.crossproduct.outgoing.adapter.db.repository.entity.ProductEntity;
import com.marketplace.crossproduct.outgoing.adapter.db.repository.mapper.ProductEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ProductPortRepositoryService implements ProductPortRepository {

    private final ProductEntityRepository productEntityRepository;
    private final ProductEntityMapper productEntityMapper;

    @Override
    public Optional<Product> getById(final Long id) {
         return productEntityRepository.findById(id).map(productEntityMapper::toProduct);
    }

    @Override
    public Set<Product> getByPortalId(final Long portalId) {
        var result = productEntityRepository.findAllByPortalId(portalId);
        return result.stream().map(productEntityMapper::toProduct).collect(Collectors.toSet());
    }

    @Override
    public Product save(String name) {
        return productEntityMapper.toProduct(productEntityRepository.save(ProductEntity.builder().name(name).build()));
    }

}


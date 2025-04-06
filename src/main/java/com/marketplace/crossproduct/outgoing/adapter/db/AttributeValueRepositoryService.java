package com.marketplace.crossproduct.outgoing.adapter.db;

import com.marketplace.crossproduct.core.model.AttributeValue;
import com.marketplace.crossproduct.core.port.db.AttributeValuePortRepository;
import com.marketplace.crossproduct.outgoing.adapter.db.repository.AttributeValueEntityRepository;
import com.marketplace.crossproduct.outgoing.adapter.db.repository.mapper.AttributeValueEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AttributeValueRepositoryService implements AttributeValuePortRepository {

    private final AttributeValueEntityRepository repository;
    private final AttributeValueEntityMapper mapper;

    @Override
    public Optional<AttributeValue> findByPortalProductDefinition(Long portalId, Long productId, Long definitionId) {
        return repository.findByProductIdAndPortalIdAndDefinitionId(portalId, productId, definitionId).map(mapper::toAttributeValue);
    }

    @Override
    public AttributeValue save(final AttributeValue entry) {
        var entity = mapper.toAttributeValueEntity(entry);
        var result = repository.save(entity);
        return mapper.toAttributeValue(result);
    }
}

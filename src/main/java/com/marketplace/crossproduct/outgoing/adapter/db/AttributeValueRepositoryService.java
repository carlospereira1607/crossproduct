package com.marketplace.crossproduct.outgoing.adapter.db;

import com.marketplace.crossproduct.core.model.AttributeValue;
import com.marketplace.crossproduct.core.port.db.AttributeValuePortRepository;
import com.marketplace.crossproduct.outgoing.adapter.db.repository.AttributeValueEntityRepository;
import com.marketplace.crossproduct.outgoing.adapter.db.repository.entity.AttributeValueEntity;
import com.marketplace.crossproduct.outgoing.adapter.db.repository.entity.AttributeValueId;
import com.marketplace.crossproduct.outgoing.adapter.db.repository.mapper.AttributeValueEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
        var id = new AttributeValueId(entry.getPortal().getId(), entry.getProduct().getId(), entry.getDefinition().getId());
        var entity = mapper.toAttributeValueEntity(entry, id);
        var result = repository.save(entity);
        return mapper.toAttributeValue(result);
    }

    @Override
    public Set<AttributeValue> findByProductIdAndPortalId(Long productId, Long portalId) {
        return repository.findByProductIdAndPortalId(productId, portalId).stream().map(mapper::toAttributeValue).collect(Collectors.toSet());
    }
}

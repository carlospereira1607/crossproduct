package com.marketplace.crossproduct.outgoing.adapter.db;

import com.marketplace.crossproduct.core.model.AttributeDefinition;
import com.marketplace.crossproduct.core.port.db.AttributeDefinitionPortRepository;
import com.marketplace.crossproduct.outgoing.adapter.db.repository.AttributeDefinitionEntityRepository;
import com.marketplace.crossproduct.outgoing.adapter.db.repository.mapper.AttributeDefinitionEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AttributeDefinitionRepositoryService implements AttributeDefinitionPortRepository {

    private final AttributeDefinitionEntityRepository attributeDefinitionEntityRepository;
    private final AttributeDefinitionEntityMapper attributeDefinitionEntityMapper;

    @Override
    public AttributeDefinition save(final AttributeDefinition attributeDefinition) {
        var attributeDefinitionEntity = attributeDefinitionEntityMapper.toAttributeDefinitionEntity(attributeDefinition);
        return attributeDefinitionEntityMapper.toAttributeDefinition(attributeDefinitionEntityRepository.save(attributeDefinitionEntity));
    }

    @Override
    public Optional<AttributeDefinition> findByPortalIdAndName(final Long portalId, final String name) {
        var existingAttributeDefinition = attributeDefinitionEntityRepository.findByNameAndPortalId(name, portalId);
        return existingAttributeDefinition.map(attributeDefinitionEntityMapper::toAttributeDefinition);
    }

    @Override
    public Optional<AttributeDefinition> findById(final Long id) {
        return attributeDefinitionEntityRepository.findById(id).map(attributeDefinitionEntityMapper::toAttributeDefinition);
    }

}

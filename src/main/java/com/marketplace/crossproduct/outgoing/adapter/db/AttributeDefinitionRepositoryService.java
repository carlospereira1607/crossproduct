package com.marketplace.crossproduct.outgoing.adapter.db;

import com.marketplace.crossproduct.core.model.AttributeDefinition;
import com.marketplace.crossproduct.core.model.AttributeDefinitionSpecificationType;
import com.marketplace.crossproduct.core.model.AttributeDefinitionType;
import com.marketplace.crossproduct.core.port.db.AttributeDefinitionPortRepository;
import com.marketplace.crossproduct.outgoing.adapter.db.repository.AttributeDefinitionEntityRepository;
import com.marketplace.crossproduct.outgoing.adapter.db.repository.mapper.AttributeDefinitionEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AttributeDefinitionRepositoryService implements AttributeDefinitionPortRepository {

    private final AttributeDefinitionEntityRepository repository;
    private final AttributeDefinitionEntityMapper attributeDefinitionEntityMapper;

    @Override
    public AttributeDefinition save(final AttributeDefinition attributeDefinition) {
        var attributeDefinitionEntity = attributeDefinitionEntityMapper.toAttributeDefinitionEntity(attributeDefinition);
        return attributeDefinitionEntityMapper.toAttributeDefinition(repository.save(attributeDefinitionEntity));
    }

    @Override
    public Optional<AttributeDefinition> findByNameAndTypeAndSpecification(final String name, final AttributeDefinitionType definitionType,
                                                                           final AttributeDefinitionSpecificationType specificationType) {
        var definition = repository.findByNameAndTypeAndSpecificationType(name, definitionType.name(), specificationType.name());
        return definition.map(attributeDefinitionEntityMapper::toAttributeDefinition);
    }

    @Override
    public Optional<AttributeDefinition> findById(final Long id) {
        return repository.findById(id).map(attributeDefinitionEntityMapper::toAttributeDefinition);
    }

}

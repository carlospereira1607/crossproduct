package com.marketplace.crossproduct.outgoing.adapter.db;

import com.marketplace.crossproduct.core.model.AttributeDefinition;
import com.marketplace.crossproduct.core.model.AttributeDefinitionType;
import com.marketplace.crossproduct.core.port.db.AttributeDefinitionPortRepository;
import com.marketplace.crossproduct.outgoing.adapter.db.repository.AttributeDefinitionEntityRepository;
import com.marketplace.crossproduct.outgoing.adapter.db.repository.mapper.AttributeDefinitionEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

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
    public Optional<AttributeDefinition> findByNameAndTypeAndSpecificationIdAndSelectableOptions(final String name, final String type,
                                                                                                 final Long specificationId,
                                                                                                 final Set<String> selectableOptions) {
        var definition = repository.findByNameAndTypeAndSpecificationIdAndSelectableOptions(name, AttributeDefinitionType.valueOf(type), specificationId, selectableOptions);
        return definition.map(attributeDefinitionEntityMapper::toAttributeDefinition);
    }

    @Override
    public Optional<AttributeDefinition> findById(final Long id) {
        return repository.findById(id).map(attributeDefinitionEntityMapper::toAttributeDefinition);
    }

}

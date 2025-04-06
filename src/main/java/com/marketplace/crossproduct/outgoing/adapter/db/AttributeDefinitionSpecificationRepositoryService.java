package com.marketplace.crossproduct.outgoing.adapter.db;

import com.marketplace.crossproduct.core.model.AttributeDefinitionSpecification;
import com.marketplace.crossproduct.core.port.db.AttributeDefinitionSpecificationPortRepository;
import com.marketplace.crossproduct.outgoing.adapter.db.repository.AttributeDefinitionSpecificationEntityRepository;
import com.marketplace.crossproduct.outgoing.adapter.db.repository.mapper.AttributeDefinitionSpecificationEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AttributeDefinitionSpecificationRepositoryService implements AttributeDefinitionSpecificationPortRepository {

    private final AttributeDefinitionSpecificationEntityRepository attributeDefinitionSpecificationEntityRepository;
    private final AttributeDefinitionSpecificationEntityMapper attributeDefinitionSpecificationEntityMapper;

    @Override
    public Optional<AttributeDefinitionSpecification> findById(final Long id) {
        var existingAttributeDefinitionSpecification = attributeDefinitionSpecificationEntityRepository.findById(id);
        return existingAttributeDefinitionSpecification.map(attributeDefinitionSpecificationEntityMapper::toAttributeDefinitionSpecification);
    }

}

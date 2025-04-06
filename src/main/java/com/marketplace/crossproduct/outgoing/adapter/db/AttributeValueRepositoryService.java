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

    private final AttributeValueEntityRepository attributeValueEntityRepository;
    private final AttributeValueEntityMapper attributeValueEntityMapper;

    @Override
    public Optional<AttributeValue> findById(final Long id) {
        return attributeValueEntityRepository.findById(id).map(attributeValueEntityMapper::toAttributeValue);
    }

    @Override
    public AttributeValue save(final AttributeValue attributeValue) {
        var entity = attributeValueEntityMapper.toAttributeValueEntity(attributeValue);
        return attributeValueEntityMapper.toAttributeValue(attributeValueEntityRepository.save(entity));
    }

}

package com.marketplace.crossproduct.core.service;

import com.marketplace.crossproduct.core.model.AttributeDefinition;
import com.marketplace.crossproduct.core.model.AttributeValue;
import com.marketplace.crossproduct.core.port.db.AttributeValuePortRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AttributeValueService {

    private final AttributeValuePortRepository attributeValuePortRepository;

    public Optional<AttributeValue> findByPortalProductDefinition(final Long portalId, final Long productId, final Long definitionId) {
        return attributeValuePortRepository.findByPortalProductDefinition(portalId, productId, definitionId);
    }

    public AttributeValue update(final AttributeValue value) {
        return attributeValuePortRepository.save(value);
    }

    public AttributeValue create(final AttributeDefinition attributeDefinition, final String value, boolean isStandard) {
        var attributeValue = AttributeValue.builder()
                .definition(attributeDefinition)
                .value(value)
                .isStandard(isStandard)
                .build();
        return attributeValuePortRepository.save(attributeValue);
    }

    public Set<AttributeValue> findByProductIdAndPortalId(Long productId, Long portalId) {
        return attributeValuePortRepository.findByProductIdAndPortalId(productId, portalId);
    }

}

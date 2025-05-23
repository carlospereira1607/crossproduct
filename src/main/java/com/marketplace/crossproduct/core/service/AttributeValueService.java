package com.marketplace.crossproduct.core.service;

import com.marketplace.crossproduct.core.model.AttributeDefinition;
import com.marketplace.crossproduct.core.model.AttributeValue;
import com.marketplace.crossproduct.core.model.Portal;
import com.marketplace.crossproduct.core.model.Product;
import com.marketplace.crossproduct.core.port.db.AttributeValuePortRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class AttributeValueService {

    private final AttributeValuePortRepository attributeValuePortRepository;

    public Optional<AttributeValue> findByPortalProductDefinition(final Long portalId, final Long productId, final Long definitionId) {
        return attributeValuePortRepository.findByPortalProductDefinition(portalId, productId, definitionId);
    }

    public void update(final Long portalId, final Long productId, final Long definitionId, final String value, boolean isStandard) {
        attributeValuePortRepository.updateValueAndIsStandard(portalId, productId, definitionId, value, isStandard);
    }

    public AttributeValue create(final AttributeDefinition attributeDefinition, final Product product, final Portal portal, final String value, boolean isStandard) {
        var attributeValue = AttributeValue.builder()
                .definition(attributeDefinition)
                .product(product)
                .portal(portal)
                .value(value)
                .isStandard(isStandard)
                .build();
        return attributeValuePortRepository.save(attributeValue);
    }

    public Set<AttributeValue> findByProductIdAndPortalId(Long productId, Long portalId) {
        return attributeValuePortRepository.findByProductIdAndPortalId(productId, portalId);
    }

    public Set<AttributeValue> findByProductId(Long productId) {
        return attributeValuePortRepository.findByProductId(productId);
    }

}

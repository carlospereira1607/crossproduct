package com.marketplace.crossproduct.core.service;

import com.marketplace.crossproduct.core.model.AttributeDefinition;
import com.marketplace.crossproduct.core.model.AttributeDefinitionSpecification;
import com.marketplace.crossproduct.core.model.AttributeDefinitionType;
import com.marketplace.crossproduct.core.port.db.AttributeDefinitionPortRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AttributeDefinitionService {

    private final AttributeDefinitionPortRepository attributeDefinitionPortRepository;

    public AttributeDefinition save(final String name, final AttributeDefinitionType type,
                                    final AttributeDefinitionSpecification specification,
                                    final Set<String> selectableOptions) {
        var attributeDefinition = AttributeDefinition.builder()
                .name(name)
                .type(type)
                .specification(specification)
                .selectableOptions(selectableOptions)
                .build();
        return attributeDefinitionPortRepository.save(attributeDefinition);
    }

    public Optional<AttributeDefinition> findByPortalIdAndName(final Long portalId, final String name) {
        return attributeDefinitionPortRepository.findByPortalIdAndName(portalId, name);
    }

    public Optional<AttributeDefinition> findById(final Long id) {
        return attributeDefinitionPortRepository.findById(id);
    }

}

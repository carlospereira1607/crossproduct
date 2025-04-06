package com.marketplace.crossproduct.core.service;

import com.marketplace.crossproduct.core.model.AttributeDefinition;
import com.marketplace.crossproduct.core.model.AttributeDefinitionSpecificationType;
import com.marketplace.crossproduct.core.model.AttributeDefinitionType;
import com.marketplace.crossproduct.core.port.db.AttributeDefinitionPortRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class AttributeDefinitionService {

    private final AttributeDefinitionPortRepository attributeDefinitionPortRepository;

    public AttributeDefinition save(final String name, final AttributeDefinitionType definitionType,
                                    final AttributeDefinitionSpecificationType specificationType,
                                    final String specificationValue,
                                    final Set<String> selectableOptions) {
        var attributeDefinition = AttributeDefinition.builder()
                .name(name)
                .definitionType(definitionType)
                .specificationType(specificationType)
                .specificationValue(specificationValue)
                .selectableOptions(selectableOptions)
                .build();
        return attributeDefinitionPortRepository.save(attributeDefinition);
    }

    public Optional<AttributeDefinition> findByNameAndTypeAndSpecificationIdAndSelectableOptions(final String name, final AttributeDefinitionType definitionType,
                                                                                                 final AttributeDefinitionSpecificationType specificationType,
                                                                                                 final String specificationValue,
                                                                                                 final Set<String> selectableOptions) {
        return attributeDefinitionPortRepository.findByNameAndTypeAndSpecificationIdAndSelectableOptions(name, definitionType, specificationType, specificationValue, selectableOptions);
    }

    public Optional<AttributeDefinition> findById(final Long id) {
        return attributeDefinitionPortRepository.findById(id);
    }

}

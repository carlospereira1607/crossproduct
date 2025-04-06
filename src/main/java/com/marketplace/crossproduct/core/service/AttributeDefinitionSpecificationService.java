package com.marketplace.crossproduct.core.service;

import com.marketplace.crossproduct.core.model.AttributeDefinitionSpecification;
import com.marketplace.crossproduct.core.port.db.AttributeDefinitionSpecificationPortRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttributeDefinitionSpecificationService {

    private final AttributeDefinitionSpecificationPortRepository attributeDefinitionSpecificationPortRepository;

    public Optional<AttributeDefinitionSpecification> findById(final Long id) {
        return attributeDefinitionSpecificationPortRepository.findById(id);
    }

}

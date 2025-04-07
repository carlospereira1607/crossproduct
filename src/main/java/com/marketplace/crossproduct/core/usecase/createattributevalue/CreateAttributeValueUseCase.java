package com.marketplace.crossproduct.core.usecase.createattributevalue;

import com.marketplace.crossproduct.core.exception.DataNotFoundException;
import com.marketplace.crossproduct.core.exception.DuplicatedEntryException;
import com.marketplace.crossproduct.core.service.AttributeDefinitionService;
import com.marketplace.crossproduct.core.service.AttributeValueService;
import com.marketplace.crossproduct.core.service.PortalService;
import com.marketplace.crossproduct.core.service.ProductService;
import com.marketplace.crossproduct.core.usecase.UseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateAttributeValueUseCase implements UseCase<CreateAttributeValueInput, CreateAttributeValueOutput> {

    private final AttributeDefinitionService attributeDefinitionService;
    private final ProductService productService;
    private final PortalService portalService;
    private final AttributeValueService attributeValueService;

    @Override
    public CreateAttributeValueOutput execute(final CreateAttributeValueInput input) {
        var definition = attributeDefinitionService.findById(input.getDefinitionId())
                                                                                .orElseThrow(() -> new DataNotFoundException("Could not find attribute definition"));
        var product = productService.findById(input.getProductId())
                .orElseThrow(() -> new DataNotFoundException("Could not find product"));

        var portal = portalService.findById(input.getPortalId())
                .orElseThrow(() -> new DataNotFoundException("Could not find portal"));

        var existingValue = attributeValueService.findByPortalProductDefinition(input.getPortalId(), input.getProductId(), input.getDefinitionId());
        if(existingValue.isPresent()) {
            log.error("Attribute value already exists for portal {} product {} and definition {}", input.getPortalId(), input.getProductId(), input.getDefinitionId());
            throw new DuplicatedEntryException("Value already exists");
        }

        var value = attributeValueService.create(definition, product, portal, input.getValue(), input.isStandard());

        return CreateAttributeValueOutput.builder()
                .value(value.getValue())
                .isStandard(value.getIsStandard())
                .definition(definition)
                .portal(portal)
                .product(product)
                .build();
    }

}

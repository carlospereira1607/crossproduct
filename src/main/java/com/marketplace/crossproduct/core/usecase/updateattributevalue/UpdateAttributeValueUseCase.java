package com.marketplace.crossproduct.core.usecase.updateattributevalue;

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
public class UpdateAttributeValueUseCase implements UseCase<UpdateAttributeValueInput, UpdateAttributeValueOutput> {

    private final AttributeValueService attributeValueService;
    private final AttributeDefinitionService attributeDefinitionService;
    private final PortalService portalService;
    private final ProductService productService;

    @Override
    public UpdateAttributeValueOutput execute(final UpdateAttributeValueInput input) {
        var existingValue = attributeValueService.findByPortalProductDefinition(input.getPortalId(),
                                                                                              input.getProductId(),
                                                                                              input.getDefinitionId())
                                                                .orElseThrow(() -> new RuntimeException("Could not find attribute specificationValue to update"));

        if(shouldUpdate(existingValue.getDefinition(), input.getDefinitionId())) {
            var newDefinition = attributeDefinitionService.findById(input.getDefinitionId()).orElseThrow(() -> new RuntimeException("Could not find attribute definition to set for specificationValue"));
            existingValue.setDefinition(newDefinition);
        }

        if(shouldUpdate(existingValue.getPortal(), input.getPortalId())) {
            var newDefinition = portalService.findById(input.getPortalId()).orElseThrow(() -> new RuntimeException("Could not find portal to set for specificationValue"));
            existingValue.setPortal(newDefinition);
        }

        if(shouldUpdate(existingValue.getProduct(), input.getProductId())) {
            var newDefinition = productService.findById(input.getPortalId()).orElseThrow(() -> new RuntimeException("Could not find product to set for specificationValue"));
            existingValue.setProduct(newDefinition);
        }

        if(shouldUpdate(existingValue.getValue(), input.getValue())) {
            existingValue.setValue(input.getValue());
        }

        if(shouldUpdate(existingValue.getIsStandard(), input.getIsStandard())) {
            existingValue.setIsStandard(input.getIsStandard());
        }

        var result = attributeValueService.update(existingValue);
        return UpdateAttributeValueOutput.builder()
                .value(result.getValue())
                .isStandard(result.getIsStandard())
                .portal(result.getPortal())
                .product(result.getProduct())
                .definition(result.getDefinition())
                .build();
    }

    private boolean shouldUpdate(final Object toBeUpdated, final Object update) {
        if(toBeUpdated == null) {
            return true;
        }

        if(update == null) {
            return false;
        }

        return !toBeUpdated.equals(update);
    }

}

package com.marketplace.crossproduct.core.usecase.updateattributevalue;

import com.marketplace.crossproduct.core.exception.DataNotFoundException;
import com.marketplace.crossproduct.core.service.AttributeValueService;
import com.marketplace.crossproduct.core.usecase.UseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UpdateAttributeValueUseCase implements UseCase<UpdateAttributeValueInput, UpdateAttributeValueOutput> {

    private final AttributeValueService attributeValueService;

    @Override
    public UpdateAttributeValueOutput execute(final UpdateAttributeValueInput input) {
        var existingValue = attributeValueService.findByPortalProductDefinition(input.getPortalId(),
                                                                                              input.getProductId(),
                                                                                              input.getDefinitionId())
                                                                .orElseThrow(() -> new DataNotFoundException("Could not find attribute value to update"));
        existingValue.setValue(input.getValue());
        existingValue.setIsStandard(input.getIsStandard());

        var result = attributeValueService.update(existingValue);
        return UpdateAttributeValueOutput.builder()
                .value(result.getValue())
                .isStandard(result.getIsStandard())
                .portal(result.getPortal())
                .product(result.getProduct())
                .definition(result.getDefinition())
                .build();
    }

}

package com.marketplace.crossproduct.core.usecase.updateattributevalue;

import com.marketplace.crossproduct.core.service.AttributeDefinitionService;
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
    private final AttributeDefinitionService attributeDefinitionService;

    @Override
    public UpdateAttributeValueOutput execute(final UpdateAttributeValueInput input) {
        var existingValue = attributeValueService.findById(input.getAttributeValueId()).orElseThrow(() -> new RuntimeException("Could not find attribute value to update"));

        if(shouldUpdate(existingValue.getDefinition(), input.getAttributeDefinitionId())) {
            var newDefinition = attributeDefinitionService.findById(input.getAttributeDefinitionId()).orElseThrow(() -> new RuntimeException("Could not find attribute definition to set for value"));
            existingValue.setDefinition(newDefinition);
        }

        if(shouldUpdate(existingValue.getValue(), input.getValue())) {
            existingValue.setValue(input.getValue());
        }

        if(shouldUpdate(existingValue.getIsStandard(), input.getIsStandard())) {
            existingValue.setIsStandard(input.getIsStandard());
        }

        var result = attributeValueService.update(existingValue);
        return UpdateAttributeValueOutput.builder()
                .id(result.getId())
                .value(result.getValue())
                .isStandard(result.getIsStandard())
                .definition(result.getDefinition())
                .build();
    }

    private boolean shouldUpdate(final Object one, final Object another) {
        if(one == null) {
            return true;
        }

        if(another == null) {
            return false;
        }

        return !one.equals(another);
    }

}

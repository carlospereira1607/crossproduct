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
        attributeValueService.findByPortalProductDefinition(input.getPortalId(), input.getProductId(), input.getDefinitionId())
                                                            .orElseThrow(() -> new DataNotFoundException("Could not find attribute value to update"));
        attributeValueService.update(input.getPortalId(), input.getProductId(), input.getDefinitionId(),
                                     input.getValue(), input.getIsStandard());
        return UpdateAttributeValueOutput.builder()
                .build();
    }

}

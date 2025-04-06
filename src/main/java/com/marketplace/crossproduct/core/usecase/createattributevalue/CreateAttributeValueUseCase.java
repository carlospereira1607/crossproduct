package com.marketplace.crossproduct.core.usecase.createattributevalue;

import com.marketplace.crossproduct.core.service.AttributeDefinitionService;
import com.marketplace.crossproduct.core.service.AttributeValueService;
import com.marketplace.crossproduct.core.usecase.UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateAttributeValueUseCase implements UseCase<CreateAttributeValueInput, CreateAttributeValueOutput> {

    private final AttributeDefinitionService attributeDefinitionService;
    private final AttributeValueService attributeValueService;

    @Override
    public CreateAttributeValueOutput execute(final CreateAttributeValueInput input) {
        var definition = attributeDefinitionService.findById(input.getAttributeDefinitionId())
                                                                                .orElseThrow(() -> new RuntimeException("Could not find attribute definition"));
        var value = attributeValueService.create(definition, input.getValue(), input.isStandard());
        return CreateAttributeValueOutput.builder()
                .id(value.getId())
                .value(value.getValue())
                .isStandard(value.getIsStandard())
                .definition(definition)
                .build();
    }

}

package com.marketplace.crossproduct.core.usecase.createattributedefinition;

import com.marketplace.crossproduct.core.model.AttributeDefinitionType;
import com.marketplace.crossproduct.core.service.AttributeDefinitionService;
import com.marketplace.crossproduct.core.service.AttributeDefinitionSpecificationService;
import com.marketplace.crossproduct.core.usecase.UseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateAttributeDefinitionUseCase implements UseCase<CreateAttributeDefinitionInput, CreateAttributeDefinitionOutput> {

    private final AttributeDefinitionService attributeDefinitionService;
    private final AttributeDefinitionSpecificationService attributeDefinitionSpecificationService;

    @Override
    public CreateAttributeDefinitionOutput execute(final CreateAttributeDefinitionInput input) {
        var existingDefinition = attributeDefinitionService.findByNameAndTypeAndSpecificationIdAndSelectableOptions(input.getName(),
                                                                                                                                             input.getType(),
                                                                                                                                             input.getSpecificationId(),
                                                                                                                                             input.getSelectableOptions());

        if(existingDefinition.isPresent()) {
            log.error("Attribute definition {} {} already exists", input.getName(), input.getType());
            throw new RuntimeException("Duplicated attribute definition");
        }

        var specification = attributeDefinitionSpecificationService.findById(input.getSpecificationId())
                .orElseThrow(() -> new RuntimeException("Could not find attribute definition specification"));

        var savedDefinition = attributeDefinitionService.save(input.getName(), AttributeDefinitionType.valueOf(input.getType()),
                specification, input.getSelectableOptions());

        return CreateAttributeDefinitionOutput.builder()
                .id(savedDefinition.getId())
                .name(savedDefinition.getName())
                .type(savedDefinition.getType())
                .specificationId(savedDefinition.getSpecification().getId())
                .selectableOptions(savedDefinition.getSelectableOptions())
                .build();
    }

}

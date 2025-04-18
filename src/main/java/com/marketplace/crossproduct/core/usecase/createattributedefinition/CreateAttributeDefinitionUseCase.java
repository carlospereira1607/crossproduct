package com.marketplace.crossproduct.core.usecase.createattributedefinition;

import com.marketplace.crossproduct.core.exception.DuplicatedEntryException;
import com.marketplace.crossproduct.core.model.AttributeDefinitionSpecificationType;
import com.marketplace.crossproduct.core.model.AttributeDefinitionType;
import com.marketplace.crossproduct.core.service.AttributeDefinitionService;
import com.marketplace.crossproduct.core.usecase.UseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateAttributeDefinitionUseCase implements UseCase<CreateAttributeDefinitionInput, CreateAttributeDefinitionOutput> {

    private final AttributeDefinitionService attributeDefinitionService;

    @Override
    public CreateAttributeDefinitionOutput execute(final CreateAttributeDefinitionInput input) {
        var definitionType = AttributeDefinitionType.valueOf(input.getDefinitionType());
        var specificationType = AttributeDefinitionSpecificationType.valueOf(input.getSpecificationType());
        var existingDefinition = attributeDefinitionService.findByNameAndTypeAndSpecification(input.getName(), definitionType, specificationType);

        if(existingDefinition.isPresent()) {
            log.error("Attribute definition {} {} already exists", input.getName(), input.getDefinitionType());
            throw new DuplicatedEntryException("Duplicated attribute definition");
        }

        var savedDefinition = attributeDefinitionService.save(input.getName(), definitionType, specificationType, input.getSpecificationValue(), input.getSelectableOptions());

        return CreateAttributeDefinitionOutput.builder()
                .id(savedDefinition.getId())
                .name(savedDefinition.getName())
                .definitionType(savedDefinition.getDefinitionType())
                .specificationType(savedDefinition.getSpecificationType())
                .value(savedDefinition.getSpecificationValue())
                .selectableOptions(savedDefinition.getSelectableOptions())
                .build();
    }

}

package com.marketplace.crossproduct.core.usecase.createattributevalue;

import com.marketplace.crossproduct.core.model.AttributeDefinition;
import com.marketplace.crossproduct.core.model.AttributeDefinitionType;
import com.marketplace.crossproduct.core.model.AttributeValue;
import com.marketplace.crossproduct.core.service.AttributeDefinitionService;
import com.marketplace.crossproduct.core.service.AttributeValueService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class CreateAttributeValueUseCaseTest {

    @Mock
    private AttributeDefinitionService attributeDefinitionService;

    @Mock
    private AttributeValueService attributeValueService;

    @InjectMocks
    private CreateAttributeValueUseCase createAttributeValueUseCase;

    @Test
    void execute_whenValidInput_shouldReturnOutput() {
        var definitionId = 1L;
        var value = "Red";
        var isStandard = true;

        var input = CreateAttributeValueInput.builder()
                .attributeDefinitionId(definitionId)
                .value(value)
                .isStandard(isStandard)
                .build();

        var definition = AttributeDefinition.builder()
                .id(definitionId)
                .name("Color")
                .type(AttributeDefinitionType.TEXT)
                .build();

        var attributeValue = AttributeValue.builder()
                .id(10L)
                .value(value)
                .isStandard(isStandard)
                .definition(definition)
                .build();

        when(attributeDefinitionService.findById(definitionId)).thenReturn(Optional.of(definition));
        when(attributeValueService.create(definition, value, isStandard)).thenReturn(attributeValue);

        var output = createAttributeValueUseCase.execute(input);

        assertNotNull(output);
        assertEquals(attributeValue.getId(), output.getId());
        assertEquals(attributeValue.getValue(), output.getValue());
        assertTrue(output.isStandard());
        assertEquals(definition, output.getDefinition());

        verify(attributeDefinitionService).findById(definitionId);
        verify(attributeValueService).create(definition, value, isStandard);

        verifyNoMoreInteractions(attributeValueService, attributeDefinitionService);
    }

    @Test
    void execute_whenDefinitionNotFound_shouldThrowException() {
        var definitionId = 1L;
        var input = CreateAttributeValueInput.builder()
                .attributeDefinitionId(definitionId)
                .value("Red")
                .isStandard(false)
                .build();

        when(attributeDefinitionService.findById(definitionId)).thenReturn(Optional.empty());

        var exception = assertThrows(RuntimeException.class, () ->
                createAttributeValueUseCase.execute(input)
        );

        assertEquals("Could not find attribute definition", exception.getMessage());

        verify(attributeDefinitionService).findById(definitionId);
        verifyNoMoreInteractions(attributeDefinitionService);
        verifyNoInteractions(attributeValueService);
    }

}

package com.marketplace.crossproduct.core.usecase.updateattributevalue;

import com.marketplace.crossproduct.core.model.AttributeDefinition;
import com.marketplace.crossproduct.core.model.AttributeValue;
import com.marketplace.crossproduct.core.service.AttributeDefinitionService;
import com.marketplace.crossproduct.core.service.AttributeValueService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateAttributeValueUseCaseTest {

    @Mock
    private AttributeValueService attributeValueService;

    @Mock
    private AttributeDefinitionService attributeDefinitionService;

    @InjectMocks
    private UpdateAttributeValueUseCase updateAttributeValueUseCase;

    @Test
    void testExecute_successfulUpdate() {
        var input = UpdateAttributeValueInput.builder()
                .attributeValueId(1L)
                .attributeDefinitionId(2L)
                .value("Updated Value")
                .isStandard(true)
                .build();

        var oldDefinition = AttributeDefinition.builder().id(1L).build();
        var newDefinition = AttributeDefinition.builder().id(2L).build();

        var existingValue = AttributeValue.builder()
                .id(1L)
                .value("Old Value")
                .isStandard(false)
                .definition(oldDefinition)
                .build();

        var updatedValue = AttributeValue.builder()
                .id(1L)
                .value("Updated Value")
                .isStandard(true)
                .definition(newDefinition)
                .build();

        when(attributeValueService.findById(1L)).thenReturn(Optional.of(existingValue));
        when(attributeDefinitionService.findById(2L)).thenReturn(Optional.of(newDefinition));
        when(attributeValueService.update(any())).thenReturn(updatedValue);

        var result = updateAttributeValueUseCase.execute(input);

        assertEquals(updatedValue.getId(), result.getId());
        assertEquals(updatedValue.getValue(), result.getValue());
        assertEquals(updatedValue.getIsStandard(), result.isStandard());
        assertEquals(updatedValue.getDefinition(), result.getDefinition());

        verify(attributeValueService).findById(1L);
        verify(attributeDefinitionService).findById(2L);
        verify(attributeValueService).update(any());

        verifyNoMoreInteractions(attributeValueService, attributeDefinitionService);
    }

    @Test
    void testExecute_attributeValueNotFound_throwsException() {
        when(attributeValueService.findById(1L)).thenReturn(Optional.empty());

        var input = UpdateAttributeValueInput.builder()
                .attributeValueId(1L)
                .attributeDefinitionId(2L)
                .value("value")
                .isStandard(true)
                .build();

        var ex = assertThrows(RuntimeException.class, () -> updateAttributeValueUseCase.execute(input));

        assertEquals("Could not find attribute value to update", ex.getMessage());

        verify(attributeValueService).findById(1L);
        verifyNoMoreInteractions(attributeValueService);
        verifyNoInteractions(attributeDefinitionService);
    }

    @Test
    void testExecute_newDefinitionNotFound_throwsException() {
        var oldDefinition = AttributeDefinition.builder().id(1L).build();
        var existingValue = AttributeValue.builder()
                .id(1L)
                .value("Old")
                .isStandard(false)
                .definition(oldDefinition)
                .build();

        when(attributeValueService.findById(1L)).thenReturn(Optional.of(existingValue));
        when(attributeDefinitionService.findById(2L)).thenReturn(Optional.empty());

        var input = UpdateAttributeValueInput.builder()
                .attributeValueId(1L)
                .attributeDefinitionId(2L)
                .value("new")
                .isStandard(true)
                .build();

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                updateAttributeValueUseCase.execute(input));

        assertEquals("Could not find attribute definition to set for value", ex.getMessage());

        verify(attributeValueService).findById(1L);
        verify(attributeDefinitionService).findById(2L);
        verifyNoMoreInteractions(attributeValueService, attributeDefinitionService);
    }

    @Test
    void testExecute_noChanges_makesNoUpdate() {
        var definition = AttributeDefinition.builder().id(1L).build();

        var existingValue = AttributeValue.builder()
                .id(1L)
                .value("Same Value")
                .isStandard(true)
                .definition(definition)
                .build();

        var input = UpdateAttributeValueInput.builder()
                .attributeValueId(1L)
                .attributeDefinitionId(1L)
                .value("Same Value")
                .isStandard(true)
                .build();
        when(attributeDefinitionService.findById(1L)).thenReturn(Optional.of(definition));
        when(attributeValueService.findById(1L)).thenReturn(Optional.of(existingValue));
        when(attributeValueService.update(any())).thenReturn(existingValue);

        var result = updateAttributeValueUseCase.execute(input);

        assertEquals(existingValue.getId(), result.getId());
        assertEquals(existingValue.getValue(), result.getValue());
        assertEquals(existingValue.getIsStandard(), result.isStandard());
        assertEquals(existingValue.getDefinition(), result.getDefinition());

        verify(attributeValueService).findById(1L);
        verify(attributeValueService).update(any());
        verify(attributeDefinitionService).findById(1L);
        verifyNoMoreInteractions(attributeDefinitionService, attributeValueService);
    }

}

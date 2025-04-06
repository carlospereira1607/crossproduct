package com.marketplace.crossproduct.core.usecase.createattributedefinition;

import com.marketplace.crossproduct.core.model.AttributeDefinition;
import com.marketplace.crossproduct.core.model.AttributeDefinitionSpecificationType;
import com.marketplace.crossproduct.core.model.AttributeDefinitionType;
import com.marketplace.crossproduct.core.service.AttributeDefinitionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class CreateAttributeDefinitionUseCaseTest {

    @Mock
    private AttributeDefinitionService attributeDefinitionService;

    @InjectMocks
    private CreateAttributeDefinitionUseCase createAttributeDefinitionUseCase;

    private CreateAttributeDefinitionInput input;
    private AttributeDefinition savedAttributeDefinition;
    private Set<String> selectableOptions;

    @BeforeEach
    void setUp() {
        selectableOptions = new HashSet<>(Arrays.asList("option1", "option2"));
        input = CreateAttributeDefinitionInput.builder()
                .name("Test Attribute")
                .definitionType("TEXT")
                .specificationType("TEXT_FORMAT")
                .value("Test Value")
                .selectableOptions(selectableOptions)
                .build();

        savedAttributeDefinition = AttributeDefinition.builder()
                .id(1L)
                .name("Test Attribute")
                .definitionType(AttributeDefinitionType.TEXT)
                .specificationType(AttributeDefinitionSpecificationType.TEXT_FORMAT)
                .value("Test Value")
                .selectableOptions(selectableOptions)
                .build();
    }

    @Test
    void testExecuteWhenAttributeDefinitionExists() {
        when(attributeDefinitionService.findByNameAndTypeAndSpecificationIdAndSelectableOptions(
                any(), any(), any(), any(), any())).thenReturn(Optional.of(savedAttributeDefinition));

        var exception = assertThrows(RuntimeException.class, () -> {
            createAttributeDefinitionUseCase.execute(input);
        });

        assertEquals("Duplicated attribute definition", exception.getMessage());
        verify(attributeDefinitionService).findByNameAndTypeAndSpecificationIdAndSelectableOptions(
                any(), any(), any(), any(), any());
        verifyNoMoreInteractions(attributeDefinitionService);
    }

    @Test
    void testExecuteWhenAttributeDefinitionDoesNotExist() {
        when(attributeDefinitionService.findByNameAndTypeAndSpecificationIdAndSelectableOptions(
                any(), any(), any(), any(), any())).thenReturn(Optional.empty());

        when(attributeDefinitionService.save(any(), any(), any(), any(), any())).thenReturn(savedAttributeDefinition);

        var output = createAttributeDefinitionUseCase.execute(input);

        assertNotNull(output);
        assertEquals(1L, output.getId());
        assertEquals("Test Attribute", output.getName());
        assertEquals(AttributeDefinitionType.TEXT, output.getDefinitionType());
        assertEquals(AttributeDefinitionSpecificationType.TEXT_FORMAT, output.getSpecificationType());
        assertEquals("Test Value", output.getValue());
        assertEquals(selectableOptions, output.getSelectableOptions());

        verify(attributeDefinitionService).findByNameAndTypeAndSpecificationIdAndSelectableOptions(
                any(), any(), any(), any(), any());
        verify(attributeDefinitionService).save(any(), any(), any(), any(), any());
    }
}

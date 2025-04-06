package com.marketplace.crossproduct.core.usecase.createattributedefinition;

import com.marketplace.crossproduct.core.model.AttributeDefinition;
import com.marketplace.crossproduct.core.model.AttributeDefinitionSpecification;
import com.marketplace.crossproduct.core.model.AttributeDefinitionType;
import com.marketplace.crossproduct.core.service.AttributeDefinitionService;
import com.marketplace.crossproduct.core.service.AttributeDefinitionSpecificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class CreateAttributeDefinitionUseCaseTest {

    @Mock
    private AttributeDefinitionService attributeDefinitionService;

    @Mock
    private AttributeDefinitionSpecificationService specificationService;

    @InjectMocks
    private CreateAttributeDefinitionUseCase useCase;

    private CreateAttributeDefinitionInput input;
    private AttributeDefinitionSpecification spec;
    private AttributeDefinition savedDef;

    @BeforeEach
    void setUp() {
        input = CreateAttributeDefinitionInput.builder()
                .name("Color")
                .type("TEXT")
                .specificationId(10L)
                .selectableOptions(Set.of("Red", "Blue"))
                .build();

        spec = AttributeDefinitionSpecification.builder()
                .id(10L)
                .build();

        savedDef = AttributeDefinition.builder()
                .id(99L)
                .name("Color")
                .type(AttributeDefinitionType.TEXT)
                .specification(spec)
                .selectableOptions(Set.of("Red", "Blue"))
                .build();
    }

    @Test
    void testExecute_successfulCreation() {
        when(attributeDefinitionService.findByNameAndTypeAndSpecificationIdAndSelectableOptions(any(), any(), any(), any())).thenReturn(Optional.empty());
        when(specificationService.findById(10L)).thenReturn(Optional.of(spec));
        when(attributeDefinitionService.save("Color", AttributeDefinitionType.TEXT, spec, Set.of("Red", "Blue"))).thenReturn(savedDef);

        var result = useCase.execute(input);

        assertNotNull(result);
        assertEquals(99L, result.getId());
        assertEquals("Color", result.getName());
        assertEquals("TEXT", result.getType().name());
        assertEquals(10L, result.getSpecificationId());
        assertEquals(Set.of("Red", "Blue"), result.getSelectableOptions());
    }

    @Test
    void testExecute_existingAttributeDefinition_throwsException() {
        when(attributeDefinitionService.findByNameAndTypeAndSpecificationIdAndSelectableOptions(any(), any(), any(), any()))
                .thenReturn(Optional.of(savedDef));

        var ex = assertThrows(RuntimeException.class, () -> useCase.execute(input));
        assertEquals("Duplicated attribute definition", ex.getMessage());
    }

    @Test
    void testExecute_specificationNotFound_throwsException() {
        when(attributeDefinitionService.findByNameAndTypeAndSpecificationIdAndSelectableOptions(any(), any(), any(), any())).thenReturn(Optional.empty());
        when(specificationService.findById(10L)).thenReturn(Optional.empty());

        var ex = assertThrows(RuntimeException.class, () -> useCase.execute(input));
        assertEquals("Could not find attribute definition specification", ex.getMessage());
    }

}

package com.marketplace.crossproduct.incoming.rest;

import com.marketplace.crossproduct.core.model.AttributeDefinition;
import com.marketplace.crossproduct.core.model.AttributeDefinitionType;
import com.marketplace.crossproduct.core.usecase.createattributedefinition.CreateAttributeDefinitionInput;
import com.marketplace.crossproduct.core.usecase.createattributedefinition.CreateAttributeDefinitionOutput;
import com.marketplace.crossproduct.core.usecase.createattributedefinition.CreateAttributeDefinitionUseCase;
import com.marketplace.crossproduct.core.usecase.createattributevalue.CreateAttributeValueInput;
import com.marketplace.crossproduct.core.usecase.createattributevalue.CreateAttributeValueOutput;
import com.marketplace.crossproduct.core.usecase.createattributevalue.CreateAttributeValueUseCase;
import com.marketplace.crossproduct.core.usecase.updateattributevalue.UpdateAttributeValueInput;
import com.marketplace.crossproduct.core.usecase.updateattributevalue.UpdateAttributeValueOutput;
import com.marketplace.crossproduct.core.usecase.updateattributevalue.UpdateAttributeValueUseCase;
import com.marketplace.crossproduct.incoming.dto.createattributedefinition.CreateAttributeDefinitionRequestDto;
import com.marketplace.crossproduct.incoming.dto.createattributedefinition.CreateAttributeDefinitionResponseDto;
import com.marketplace.crossproduct.incoming.dto.createattributevalue.CreateAttributeValueRequestDto;
import com.marketplace.crossproduct.incoming.dto.createattributevalue.CreateAttributeValueResponseDto;
import com.marketplace.crossproduct.incoming.dto.updateattributevalue.UpdateAttributeValueRequestDto;
import com.marketplace.crossproduct.incoming.dto.updateattributevalue.UpdateAttributeValueResponseDto;
import com.marketplace.crossproduct.incoming.mapper.CreateAttributeDefinitionMapper;
import com.marketplace.crossproduct.incoming.mapper.CreateAttributeValueMapper;
import com.marketplace.crossproduct.incoming.mapper.UpdateAttributeValueMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class AttributeControllerTest {

    @Mock
    private CreateAttributeDefinitionUseCase createAttributeDefinitionUseCase;
    @Mock
    private CreateAttributeValueUseCase createAttributeValueUseCase;
    @Mock
    private UpdateAttributeValueUseCase updateAttributeValueUseCase;

    @Mock
    private CreateAttributeDefinitionMapper createAttributeDefinitionMapper;
    @Mock
    private CreateAttributeValueMapper createAttributeValueMapper;
    @Mock
    private UpdateAttributeValueMapper updateAttributeValueMapper;

    @InjectMocks
    private AttributeController attributeController;

    @Test
    void testCreateAttributeDefinition_success() {
        var requestDto = new CreateAttributeDefinitionRequestDto(
                1L, "Color", "TEXT", 1L, Set.of("Red", "Blue")
        );

        var input = CreateAttributeDefinitionInput.builder()
                .name("Color")
                .type("TEXT")
                .specificationId(10L)
                .selectableOptions(Set.of("Red", "Blue"))
                .build();

        var output = CreateAttributeDefinitionOutput.builder()
                .id(123L)
                .name("Color")
                .type(AttributeDefinitionType.TEXT)
                .specificationId(10L)
                .selectableOptions(Set.of("Red", "Blue"))
                .build();

        var expectedResponse = new CreateAttributeDefinitionResponseDto(
                123L, "Color", AttributeDefinitionType.TEXT, 10L, Set.of("Red", "Blue")
        );

        when(createAttributeDefinitionMapper.toCreateAttributeDefinitionInput(requestDto)).thenReturn(input);
        when(createAttributeDefinitionUseCase.execute(input)).thenReturn(output);
        when(createAttributeDefinitionMapper.toCreateAttributeDefinitionResponseDto(output)).thenReturn(expectedResponse);

        ResponseEntity<CreateAttributeDefinitionResponseDto> response = attributeController.createAttributeDefinition(requestDto);

        assertNotNull(response);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(expectedResponse, response.getBody());

        verify(createAttributeDefinitionMapper).toCreateAttributeDefinitionInput(requestDto);
        verify(createAttributeDefinitionUseCase).execute(input);
        verify(createAttributeDefinitionMapper).toCreateAttributeDefinitionResponseDto(output);

        verifyNoMoreInteractions(createAttributeDefinitionMapper, createAttributeDefinitionUseCase);
    }

    @Test
    void testCreateAttributeDefinition_duplicate_shouldThrowException() {
        var requestDto = new CreateAttributeDefinitionRequestDto(1L, "Color", "TEXT", 10L, Set.of("Red", "Blue")
        );

        var input = CreateAttributeDefinitionInput.builder()
                .name("Color")
                .type("TEXT")
                .specificationId(10L)
                .selectableOptions(Set.of("Red", "Blue"))
                .build();

        when(createAttributeDefinitionMapper.toCreateAttributeDefinitionInput(requestDto)).thenReturn(input);
        when(createAttributeDefinitionUseCase.execute(input))
                .thenThrow(new RuntimeException("Duplicated attribute definition"));

        var exception = assertThrows(RuntimeException.class, () ->
                attributeController.createAttributeDefinition(requestDto)
        );

        assertEquals("Duplicated attribute definition", exception.getMessage());

        verify(createAttributeDefinitionMapper).toCreateAttributeDefinitionInput(requestDto);
        verify(createAttributeDefinitionUseCase).execute(input);
        verifyNoMoreInteractions(createAttributeDefinitionUseCase, createAttributeDefinitionMapper);
    }


    @Test
    void testCreateAttributeValue_success() {
        var requestDto = new CreateAttributeValueRequestDto(1L, 1L, 1L, "Red", true);

        var input = CreateAttributeValueInput.builder()
                .definitionId(1L)
                .portalId(1L)
                .productId(1L)
                .value("Red")
                .isStandard(true)
                .build();

        var output = CreateAttributeValueOutput.builder()
                .id(100L)
                .value("Red")
                .isStandard(true)
                .definition(AttributeDefinition.builder().id(1L).build())
                .build();

        var definition = AttributeDefinition.builder().build();

        var responseDto = new CreateAttributeValueResponseDto(100L, "Red", true, definition);

        when(createAttributeValueMapper.toCreateAttributeValueInput(requestDto)).thenReturn(input);
        when(createAttributeValueUseCase.execute(input)).thenReturn(output);
        when(createAttributeValueMapper.toCreateAttributeValueResponseDto(output)).thenReturn(responseDto);

        var response = attributeController.createAttributeValue(requestDto);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(responseDto, response.getBody());

        verify(createAttributeValueMapper).toCreateAttributeValueInput(requestDto);
        verify(createAttributeValueUseCase).execute(input);
        verify(createAttributeValueMapper).toCreateAttributeValueResponseDto(output);

        verifyNoMoreInteractions(createAttributeValueMapper, createAttributeValueUseCase);
    }

    @Test
    void testCreateAttributeValue_failure() {
        var requestDto = new CreateAttributeValueRequestDto(1L, 1L, 1L, "Red", false);
        var input = CreateAttributeValueInput.builder()
                .definitionId(1L)
                .portalId(1L)
                .productId(1L)
                .value("Red")
                .isStandard(false)
                .build();

        when(createAttributeValueMapper.toCreateAttributeValueInput(requestDto)).thenReturn(input);
        when(createAttributeValueUseCase.execute(input))
                .thenThrow(new RuntimeException("Could not find attribute definition"));

        var exception = assertThrows(RuntimeException.class, () -> attributeController.createAttributeValue(requestDto));

        assertEquals("Could not find attribute definition", exception.getMessage());

        verify(createAttributeValueMapper).toCreateAttributeValueInput(requestDto);
        verify(createAttributeValueUseCase).execute(input);
        verifyNoMoreInteractions(createAttributeValueMapper, createAttributeValueUseCase);
    }

    @Test
    void testUpdateAttributeValue_success() {
        var definition = AttributeDefinition.builder().build();

        var request = new UpdateAttributeValueRequestDto(definition.getId(), 1L, 1L, "Blue", false);
        var input = UpdateAttributeValueInput.builder()
                .value("Blue")
                .isStandard(false)
                .build();
        var output = UpdateAttributeValueOutput.builder().value("Blue").isStandard(false).build();
        var response = new UpdateAttributeValueResponseDto(1L, "Blue", false, definition);

        when(updateAttributeValueMapper.toUpdateAttributeValueInput(request)).thenReturn(input);
        when(updateAttributeValueUseCase.execute(input)).thenReturn(output);
        when(updateAttributeValueMapper.toUpdateAttributeValueResponseDto(output)).thenReturn(response);

        var result = attributeController.updateAttributeValue(request);

        assertTrue(result.getStatusCode().is2xxSuccessful());
        assertEquals(response, result.getBody());

        verify(updateAttributeValueUseCase).execute(input);
        verifyNoMoreInteractions(updateAttributeValueUseCase);
        verifyNoInteractions(createAttributeValueUseCase, createAttributeDefinitionUseCase, createAttributeDefinitionMapper, createAttributeValueMapper);
    }

}

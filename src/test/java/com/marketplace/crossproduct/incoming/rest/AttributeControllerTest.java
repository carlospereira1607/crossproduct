package com.marketplace.crossproduct.incoming.rest;

import com.marketplace.crossproduct.core.model.AttributeDefinition;
import com.marketplace.crossproduct.core.model.AttributeDefinitionSpecificationType;
import com.marketplace.crossproduct.core.model.AttributeDefinitionType;
import com.marketplace.crossproduct.core.usecase.createattributedefinition.CreateAttributeDefinitionInput;
import com.marketplace.crossproduct.core.usecase.createattributedefinition.CreateAttributeDefinitionOutput;
import com.marketplace.crossproduct.core.usecase.createattributedefinition.CreateAttributeDefinitionUseCase;
import com.marketplace.crossproduct.core.usecase.createattributevalue.CreateAttributeValueInput;
import com.marketplace.crossproduct.core.usecase.createattributevalue.CreateAttributeValueOutput;
import com.marketplace.crossproduct.core.usecase.createattributevalue.CreateAttributeValueUseCase;
import com.marketplace.crossproduct.incoming.dto.createattributedefinition.CreateAttributeDefinitionRequestDto;
import com.marketplace.crossproduct.incoming.dto.createattributedefinition.CreateAttributeDefinitionResponseDto;
import com.marketplace.crossproduct.incoming.dto.createattributevalue.AttributeDefinitionDto;
import com.marketplace.crossproduct.incoming.dto.createattributevalue.CreateAttributeValueRequestDto;
import com.marketplace.crossproduct.incoming.dto.createattributevalue.CreateAttributeValueResponseDto;
import com.marketplace.crossproduct.incoming.dto.createattributevalue.PortalDto;
import com.marketplace.crossproduct.incoming.dto.createattributevalue.ProductDto;
import com.marketplace.crossproduct.incoming.mapper.CreateAttributeDefinitionMapper;
import com.marketplace.crossproduct.incoming.mapper.CreateAttributeValueMapper;
import com.marketplace.crossproduct.incoming.mapper.UpdateAttributeValueMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
    private CreateAttributeDefinitionMapper createAttributeDefinitionMapper;
    @Mock
    private CreateAttributeValueMapper createAttributeValueMapper;

    @InjectMocks
    private AttributeController attributeController;

    @Test
    void testCreateAttributeDefinition_success() {
        var requestDto = new CreateAttributeDefinitionRequestDto("Color", "TEXT", "TEXT_FORMAT",
                "something",  Set.of("Red", "Blue"));

        var input = CreateAttributeDefinitionInput.builder()
                .name("Color")
                .definitionType("TEXT")
                .specificationType("TEXT_FORMAT")
                .specificationValue("something")
                .selectableOptions(Set.of("Red", "Blue"))
                .build();

        var output = CreateAttributeDefinitionOutput.builder()
                .id(123L)
                .name("Color")
                .definitionType(AttributeDefinitionType.TEXT)
                .specificationType(AttributeDefinitionSpecificationType.TEXT_FORMAT)
                .value("something")
                .selectableOptions(Set.of("Red", "Blue"))
                .build();

        var expectedResponse = new CreateAttributeDefinitionResponseDto(
                123L,"Color", AttributeDefinitionType.TEXT, AttributeDefinitionSpecificationType.TEXT_FORMAT, "something",  Set.of("Red", "Blue")
        );

        when(createAttributeDefinitionMapper.toCreateAttributeDefinitionInput(requestDto)).thenReturn(input);
        when(createAttributeDefinitionUseCase.execute(input)).thenReturn(output);
        when(createAttributeDefinitionMapper.toCreateAttributeDefinitionResponseDto(output)).thenReturn(expectedResponse);

        var response = attributeController.createAttributeDefinition(requestDto);

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
        var requestDto = new CreateAttributeDefinitionRequestDto("Color", "TEXT", "TEXT_FORMAT", "something", Set.of("Red", "Blue"));

        var input = CreateAttributeDefinitionInput.builder()
                .name("Color")
                .definitionType("TEXT")
                .specificationType("TEXT_FORMAT")
                .specificationValue("something")
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

        var definitionDto = new AttributeDefinitionDto(null, null, null, null, null, null);
        var portalDto = new PortalDto(null, null);
        var productDto = new ProductDto(null, null);
        var responseDto = new CreateAttributeValueResponseDto("specificationValue", true, portalDto, productDto, definitionDto);

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

}

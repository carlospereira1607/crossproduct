package com.marketplace.crossproduct.core.usecase.createattributevalue;

import com.marketplace.crossproduct.core.model.AttributeDefinition;
import com.marketplace.crossproduct.core.model.AttributeValue;
import com.marketplace.crossproduct.core.model.Portal;
import com.marketplace.crossproduct.core.model.Product;
import com.marketplace.crossproduct.core.service.AttributeDefinitionService;
import com.marketplace.crossproduct.core.service.AttributeValueService;
import com.marketplace.crossproduct.core.service.PortalService;
import com.marketplace.crossproduct.core.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class CreateAttributeValueUseCaseTest {

    @Mock
    private AttributeDefinitionService attributeDefinitionService;

    @Mock
    private AttributeValueService attributeValueService;

    @Mock
    private ProductService productService;

    @Mock
    private PortalService portalService;

    @InjectMocks
    private CreateAttributeValueUseCase createAttributeValueUseCase;

    @Test
    void testExecute_success() {
        var definitionId = 1L;
        var productId = 1L;
        var portalId = 1L;
        var value = "Test Value";
        var isStandard = true;

        var input = new CreateAttributeValueInput(definitionId, productId, portalId, value, isStandard);

        var definition = new AttributeDefinition();
        var product = new Product();
        var portal = new Portal();
        var attributeValue = new AttributeValue(value, isStandard, portal, product, definition);

        when(attributeDefinitionService.findById(definitionId)).thenReturn(Optional.of(definition));
        when(productService.findById(productId)).thenReturn(Optional.of(product));
        when(portalService.findById(portalId)).thenReturn(Optional.of(portal));
        when(attributeValueService.create(definition, value, isStandard)).thenReturn(attributeValue);

        var output = createAttributeValueUseCase.execute(input);

        assertNotNull(output);
        assertEquals(value, output.getValue());
        assertEquals(isStandard, output.isStandard());
        assertEquals(definition, output.getDefinition());
        assertEquals(portal, output.getPortal());
        assertEquals(product, output.getProduct());

        verify(attributeDefinitionService).findById(definitionId);
        verify(productService).findById(productId);
        verify(portalService).findById(portalId);
        verify(attributeValueService).create(definition, value, isStandard);
    }

    @Test
    void testExecute_definitionNotFound() {
        var definitionId = 1L;
        var productId = 1L;
        var portalId = 1L;
        var value = "Test Value";
        var isStandard = true;

        var input = new CreateAttributeValueInput(definitionId, productId, portalId, value, isStandard);

        when(attributeDefinitionService.findById(definitionId)).thenReturn(Optional.empty());

        var exception = assertThrows(RuntimeException.class, () -> {
            createAttributeValueUseCase.execute(input);
        });

        assertEquals("Could not find attribute definition", exception.getMessage());
    }

    @Test
    void testExecute_productNotFound() {
        var definitionId = 1L;
        var productId = 1L;
        var portalId = 1L;
        var value = "Test Value";
        var isStandard = true;

        var input = new CreateAttributeValueInput(definitionId, productId, portalId, value, isStandard);

        var definition = new AttributeDefinition();
        var portal = new Portal();

        when(attributeDefinitionService.findById(definitionId)).thenReturn(Optional.of(definition));
        when(productService.findById(productId)).thenReturn(Optional.empty());
        when(portalService.findById(portalId)).thenReturn(Optional.of(portal));

        var exception = assertThrows(RuntimeException.class, () -> createAttributeValueUseCase.execute(input));

        assertEquals("Could not find product", exception.getMessage());
    }

    @Test
    void testExecute_portalNotFound() {
        var definitionId = 1L;
        var productId = 1L;
        var portalId = 1L;
        var value = "Test Value";
        var isStandard = true;

        var input = new CreateAttributeValueInput(definitionId, productId, portalId, value, isStandard);

        var definition = new AttributeDefinition();
        var product = new Product();

        when(attributeDefinitionService.findById(definitionId)).thenReturn(Optional.of(definition));
        when(productService.findById(productId)).thenReturn(Optional.of(product));
        when(portalService.findById(portalId)).thenReturn(Optional.empty());

        var exception = assertThrows(RuntimeException.class, () -> {
            createAttributeValueUseCase.execute(input);
        });

        assertEquals("Could not find portal", exception.getMessage());
    }

    @Test
    void testExecute_createAttributeValueFails() {
        var definitionId = 1L;
        var productId = 1L;
        var portalId = 1L;
        var value = "Test Value";
        var isStandard = true;

        var input = new CreateAttributeValueInput(definitionId, productId, portalId, value, isStandard);

        var definition = new AttributeDefinition();
        var product = new Product();
        var portal = new Portal();

        when(attributeDefinitionService.findById(definitionId)).thenReturn(Optional.of(definition));
        when(productService.findById(productId)).thenReturn(Optional.of(product));
        when(portalService.findById(portalId)).thenReturn(Optional.of(portal));
        when(attributeValueService.create(definition, value, isStandard)).thenThrow(new RuntimeException("Failed to create attribute value"));

        var exception = assertThrows(RuntimeException.class, () -> {
            createAttributeValueUseCase.execute(input);
        });

        assertEquals("Failed to create attribute value", exception.getMessage());
    }

}

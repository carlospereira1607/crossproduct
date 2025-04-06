package com.marketplace.crossproduct.core.usecase.updateattributevalue;

import com.marketplace.crossproduct.core.model.AttributeDefinition;
import com.marketplace.crossproduct.core.model.AttributeValue;
import com.marketplace.crossproduct.core.model.Portal;
import com.marketplace.crossproduct.core.model.Product;
import com.marketplace.crossproduct.core.service.AttributeDefinitionService;
import com.marketplace.crossproduct.core.service.AttributeValueService;
import com.marketplace.crossproduct.core.service.PortalService;
import com.marketplace.crossproduct.core.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateAttributeValueUseCaseTest {

    @Mock
    private AttributeValueService attributeValueService;

    @Mock
    private AttributeDefinitionService attributeDefinitionService;

    @Mock
    private PortalService portalService;

    @Mock
    private ProductService productService;

    @InjectMocks
    private UpdateAttributeValueUseCase updateAttributeValueUseCase;

    private UpdateAttributeValueInput input;
    private AttributeValue existingValue;
    private AttributeDefinition definition;
    private Portal portal;
    private Product product;

    @BeforeEach
    void setUp() {
        input = new UpdateAttributeValueInput("newValue",true, 1L, 1L, 1L);

        definition = new AttributeDefinition();
        portal = new Portal();
        product = new Product();

        existingValue = new AttributeValue();
        existingValue.setDefinition(definition);
        existingValue.setPortal(portal);
        existingValue.setProduct(product);
        existingValue.setValue("oldValue");
        existingValue.setIsStandard(false);
    }

    @Test
    void testExecute_SuccessfullyUpdates() {
        when(attributeValueService.findByPortalProductDefinition(input.getPortalId(), input.getProductId(), input.getDefinitionId()))
                .thenReturn(Optional.of(existingValue));

        when(attributeDefinitionService.findById(input.getDefinitionId())).thenReturn(Optional.of(definition));
        when(portalService.findById(input.getPortalId())).thenReturn(Optional.of(portal));
        when(productService.findById(input.getProductId())).thenReturn(Optional.of(product));
        when(attributeValueService.update(existingValue)).thenReturn(existingValue);

        UpdateAttributeValueOutput result = updateAttributeValueUseCase.execute(input);

        assertEquals("newValue", result.getValue());
        assertTrue(result.isStandard());
        assertEquals(portal, result.getPortal());
        assertEquals(product, result.getProduct());
        assertEquals(definition, result.getDefinition());

        verify(attributeValueService).update(existingValue);
    }

    @Test
    void testExecute_ThrowsExceptionWhenAttributeValueNotFound() {
        when(attributeValueService.findByPortalProductDefinition(input.getPortalId(), input.getProductId(), input.getDefinitionId()))
                .thenReturn(Optional.empty());

        var exception = assertThrows(RuntimeException.class, () -> {
            updateAttributeValueUseCase.execute(input);
        });

        assertEquals("Could not find attribute value to update", exception.getMessage());
    }

    @Test
    void testExecute_ThrowsExceptionWhenDefinitionNotFound() {
        when(attributeValueService.findByPortalProductDefinition(input.getPortalId(), input.getProductId(), input.getDefinitionId()))
                .thenReturn(Optional.of(existingValue));
        when(attributeDefinitionService.findById(input.getDefinitionId())).thenReturn(Optional.empty());

        var exception = assertThrows(RuntimeException.class, () -> {
            updateAttributeValueUseCase.execute(input);
        });

        assertEquals("Could not find attribute definition to set for value", exception.getMessage());
    }

    @Test
    void testExecute_ThrowsExceptionWhenPortalNotFound() {
        when(attributeValueService.findByPortalProductDefinition(input.getPortalId(), input.getProductId(), input.getDefinitionId()))
                .thenReturn(Optional.of(existingValue));
        when(attributeDefinitionService.findById(input.getDefinitionId())).thenReturn(Optional.of(definition));
        when(portalService.findById(input.getPortalId())).thenReturn(Optional.empty());

        var exception = assertThrows(RuntimeException.class, () -> {
            updateAttributeValueUseCase.execute(input);
        });

        assertEquals("Could not find portal to set for value", exception.getMessage());
    }

    @Test
    void testExecute_ThrowsExceptionWhenProductNotFound() {
        when(attributeValueService.findByPortalProductDefinition(input.getPortalId(), input.getProductId(), input.getDefinitionId()))
                .thenReturn(Optional.of(existingValue));
        when(attributeDefinitionService.findById(input.getDefinitionId())).thenReturn(Optional.of(definition));
        when(portalService.findById(input.getPortalId())).thenReturn(Optional.of(portal));
        when(productService.findById(input.getProductId())).thenReturn(Optional.empty());

        var exception = assertThrows(RuntimeException.class, () -> {
            updateAttributeValueUseCase.execute(input);
        });

        assertEquals("Could not find product to set for value", exception.getMessage());
    }

}

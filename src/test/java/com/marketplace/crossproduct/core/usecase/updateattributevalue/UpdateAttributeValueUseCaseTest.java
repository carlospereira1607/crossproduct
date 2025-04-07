package com.marketplace.crossproduct.core.usecase.updateattributevalue;

import com.marketplace.crossproduct.core.model.AttributeDefinition;
import com.marketplace.crossproduct.core.model.AttributeValue;
import com.marketplace.crossproduct.core.model.Portal;
import com.marketplace.crossproduct.core.model.Product;
import com.marketplace.crossproduct.core.service.AttributeValueService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateAttributeValueUseCaseTest {

    @Mock
    private AttributeValueService attributeValueService;

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
    void testExecute_ThrowsExceptionWhenAttributeValueNotFound() {
        when(attributeValueService.findByPortalProductDefinition(input.getPortalId(), input.getProductId(), input.getDefinitionId()))
                .thenReturn(Optional.empty());

        var exception = assertThrows(RuntimeException.class, () -> {
            updateAttributeValueUseCase.execute(input);
        });

        assertEquals("Could not find attribute value to update", exception.getMessage());
    }

}

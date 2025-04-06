package com.marketplace.crossproduct.core.usecase.getproductstandardattributes;

import com.marketplace.crossproduct.core.model.AttributeDefinition;
import com.marketplace.crossproduct.core.model.AttributeValue;
import com.marketplace.crossproduct.core.model.Product;
import com.marketplace.crossproduct.core.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class GetProductStandardAttributesUseCaseTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private GetProductStandardAttributesUseCase getProductStandardAttributesUseCase;

    private Product product;

    @BeforeEach
    void setUp() {
        var attributes = new HashSet<AttributeValue>();
        attributes.add(new AttributeValue(1L, "attr1", true, AttributeDefinition.builder().build(), Product.builder().build()));
        attributes.add(new AttributeValue(2L, "attr2", false, AttributeDefinition.builder().build(), Product.builder().build()));
        product = new Product(1L, "Product 1", attributes);
    }

    @Test
    void testExecute_success() {
        var productId = 1L;
        var input = new GetProductStandardAttributesInput(productId);
        when(productService.getById(productId)).thenReturn(Optional.of(product));

        var output = getProductStandardAttributesUseCase.execute(input);

        assertNotNull(output);
        assertNotNull(output.getProduct());
        assertEquals(1, output.getProduct().getAttributes().size());
        assertTrue(output.getProduct().getAttributes().stream().anyMatch(AttributeValue::getIsStandard));
        verify(productService).getById(productId);
    }

    @Test
    void testExecute_productNotFound() {
        var productId = 1L;
        var input = new GetProductStandardAttributesInput(productId);
        when(productService.getById(productId)).thenReturn(Optional.empty());

        var exception = assertThrows(RuntimeException.class, () -> {
            getProductStandardAttributesUseCase.execute(input);
        });
        assertEquals("Product does not exist", exception.getMessage());
        verify(productService).getById(productId);
    }

    @Test
    void testFilterForStandardAttributes() {
        var standardAttribute = new AttributeValue(1L, "Standard Attribute", true, AttributeDefinition.builder().build(), Product.builder().build());
        var nonStandardAttribute = new AttributeValue(2L, "Non-standard Attribute", false, AttributeDefinition.builder().build(), Product.builder().build());
        var mixedAttributes = new HashSet<AttributeValue>();
        mixedAttributes.add(standardAttribute);
        mixedAttributes.add(nonStandardAttribute);

        var filteredAttributes = getProductStandardAttributesUseCase.filterForStandard(mixedAttributes);

        assertEquals(1, filteredAttributes.size());
        assertTrue(filteredAttributes.contains(standardAttribute));
        assertFalse(filteredAttributes.contains(nonStandardAttribute));
    }

}

package com.marketplace.crossproduct.core.usecase.getproductdetails;

import com.marketplace.crossproduct.core.model.AttributeValue;
import com.marketplace.crossproduct.core.model.Product;
import com.marketplace.crossproduct.core.service.AttributeValueService;
import com.marketplace.crossproduct.core.service.ProductService;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class GetProductDetailsUseCaseTest {

    @Mock
    private ProductService productService;

    @Mock
    private AttributeValueService attributeValueService;

    @InjectMocks
    private GetProductDetailsUseCase getProductDetailsUseCase;

    private GetProductDetailsInput input;

    @BeforeEach
    void setUp() {
        input = new GetProductDetailsInput(1L, 1L, 1L);
    }

    @Test
    void testExecute_success() {
        var product = new Product(1L, "Product A", Set.of(AttributeValue.builder().build()));
        when(attributeValueService.findByProductIdAndPortalId(any(), any())).thenReturn(Set.of(AttributeValue.builder().build()));
        when(productService.findById(input.getProductId())).thenReturn(Optional.of(product));

        var output = getProductDetailsUseCase.execute(input);

        assertNotNull(output);
        assertEquals(product.getId(), output.getProduct().getId());
        assertEquals(product.getName(), output.getProduct().getName());
        assertEquals(product.getAttributes().size(), output.getProduct().getAttributes().size());

        verify(attributeValueService).findByProductIdAndPortalId(any(), any());
        verify(productService).findById(any());
        verifyNoMoreInteractions(productService, attributeValueService);
    }

    @Test
    void testExecute_productNotFound() {
        when(attributeValueService.findByProductIdAndPortalId(any(), any())).thenReturn(Set.of(AttributeValue.builder().build()));
        when(productService.findById(any())).thenReturn(Optional.empty());

        var thrown = assertThrows(RuntimeException.class, () -> {
            getProductDetailsUseCase.execute(input);
        });

        assertEquals("Could not find product", thrown.getMessage());
        verify(attributeValueService).findByProductIdAndPortalId(any(), any());
        verify(productService).findById(any());
        verifyNoMoreInteractions(productService);
    }

    @Test
    void testExecute_attributeValuesNotFound() {
        when(attributeValueService.findByProductIdAndPortalId(any(), any())).thenReturn(Set.of());

        var thrown = assertThrows(RuntimeException.class, () -> {
            getProductDetailsUseCase.execute(input);
        });

        assertEquals("Could not find any values for product 1 and portal 1", thrown.getMessage());
        verify(attributeValueService).findByProductIdAndPortalId(any(), any());
        verifyNoMoreInteractions(attributeValueService);
        verifyNoInteractions(productService);
    }

}

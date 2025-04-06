package com.marketplace.crossproduct.incoming.rest;

import com.marketplace.crossproduct.core.model.AttributeValue;
import com.marketplace.crossproduct.core.model.Product;
import com.marketplace.crossproduct.core.usecase.getproductstandardattributes.GetProductStandardAttributesOutput;
import com.marketplace.crossproduct.core.usecase.getproductstandardattributes.GetProductStandardAttributesUseCase;
import com.marketplace.crossproduct.incoming.dto.getproductdetails.GetProductDetailsResponseDto;
import com.marketplace.crossproduct.incoming.mapper.ProductMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Set;

import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private GetProductStandardAttributesUseCase getProductStandardAttributesUseCase;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductController productController;

    @Test
    void getStandardAttributes_success() {
        var productId = 1L;
        var standardAttribute = new AttributeValue(1L, "Standard Attribute", true, null, null);
        var product = Product.builder().id(productId).name("Test Product").attributes(Set.of(standardAttribute)).build();

        var expectedResponse = new GetProductDetailsResponseDto(product.getId(), product.getName(), null);

        var output = GetProductStandardAttributesOutput.builder().product(product).build();

        when(getProductStandardAttributesUseCase.execute(any())).thenReturn(output);
        when(productMapper.toGetProductDetailsResponseDto(any())).thenReturn(expectedResponse);

        var response = productController.getStandardAttributes(productId);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(expectedResponse, response.getBody());

        verify(getProductStandardAttributesUseCase).execute(any());
        verify(productMapper).toGetProductDetailsResponseDto(any());
        verifyNoMoreInteractions(getProductStandardAttributesUseCase, productMapper);
    }

    @Test
    void getStandardAttributes_productNotFound() {
        var productId = 1L;

        when(getProductStandardAttributesUseCase.execute(any())).thenThrow(new RuntimeException("Product does not exist"));

        try {
            productController.getStandardAttributes(productId);
            fail("Expected an exception to be thrown");
        } catch (RuntimeException e) {
            assertEquals("Product does not exist", e.getMessage());
        }

        verify(getProductStandardAttributesUseCase).execute(any());
        verifyNoInteractions(productMapper);
    }
}
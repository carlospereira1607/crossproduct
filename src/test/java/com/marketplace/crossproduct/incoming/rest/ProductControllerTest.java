package com.marketplace.crossproduct.incoming.rest;

import com.marketplace.crossproduct.core.model.AttributeDefinition;
import com.marketplace.crossproduct.core.model.AttributeValue;
import com.marketplace.crossproduct.core.model.Portal;
import com.marketplace.crossproduct.core.model.Product;
import com.marketplace.crossproduct.core.usecase.getproductdetails.GetProductDetailsInput;
import com.marketplace.crossproduct.core.usecase.getproductdetails.GetProductDetailsOutput;
import com.marketplace.crossproduct.core.usecase.getproductdetails.GetProductDetailsUseCase;
import com.marketplace.crossproduct.core.usecase.getproductstandardattributes.GetProductStandardAttributesOutput;
import com.marketplace.crossproduct.core.usecase.getproductstandardattributes.GetProductStandardAttributesUseCase;
import com.marketplace.crossproduct.incoming.dto.getproductdetails.GetProductDetailsResponseDto;
import com.marketplace.crossproduct.incoming.mapper.ProductMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Set;

import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
class ProductControllerTest {

    @Mock
    private GetProductStandardAttributesUseCase getProductStandardAttributesUseCase;

    @Mock
    private GetProductDetailsUseCase getProductDetailsUseCase;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductController productController;

    @Test
    void getStandardAttributes_success() {
        var productId = 1L;
        var standardAttribute = new AttributeValue("attr1", true,
                Portal.builder().build(), Product.builder().build(), AttributeDefinition.builder().build()
        );
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

    @Test
    void testGetProductDetails_success() {
        var productId = 1L;
        var product = new Product(productId, "Sample Product", null);
        var productDetails = new GetProductDetailsResponseDto(product.getId(), product.getName(), null);

        when(getProductDetailsUseCase.execute(any(GetProductDetailsInput.class)))
                .thenReturn(new GetProductDetailsOutput(product));
        when(productMapper.toGetProductDetailsResponseDto(any(Product.class)))
                .thenReturn(productDetails);

        var response = productController.getProductDetails(1L, 1L, 1L);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(productDetails, response.getBody());
    }

}
package com.marketplace.crossproduct.incoming.rest;

import com.marketplace.crossproduct.core.model.Product;
import com.marketplace.crossproduct.core.usecase.getproductdetails.GetProductDetailsInput;
import com.marketplace.crossproduct.core.usecase.getproductdetails.GetProductDetailsOutput;
import com.marketplace.crossproduct.core.usecase.getproductdetails.GetProductDetailsUseCase;
import com.marketplace.crossproduct.core.usecase.getproductsbyportal.GetProductsByPortalInput;
import com.marketplace.crossproduct.core.usecase.getproductsbyportal.GetProductsByPortalOutput;
import com.marketplace.crossproduct.core.usecase.getproductsbyportal.GetProductsByPortalUseCase;
import com.marketplace.crossproduct.core.usecase.getproductsbyportal.ProductIdAndName;
import com.marketplace.crossproduct.incoming.dto.getproductdetails.GetProductDetailsResponseDto;
import com.marketplace.crossproduct.incoming.mapper.ProductMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class PortalControllerTest {

    @Mock
    private GetProductsByPortalUseCase getProductsByPortalUseCase;

    @Mock
    private GetProductDetailsUseCase getProductDetailsUseCase;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private PortalController portalController;

    @Test
    void testGetProducts_Success() {
        var portalId = 1L;
        var productSet = new HashSet<ProductIdAndName>();
        productSet.add(new ProductIdAndName(1L, "Product 1"));
        productSet.add(new ProductIdAndName(2L, "Product 2"));

        var output = new GetProductsByPortalOutput(productSet);
        when(getProductsByPortalUseCase.execute(any(GetProductsByPortalInput.class)))
                .thenReturn(output);

        var response = portalController.getProducts(portalId);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(2, response.getBody().size());
        assertTrue(response.getBody().stream().anyMatch(dto -> dto.name().equals("Product 1")));
        assertTrue(response.getBody().stream().anyMatch(dto -> dto.name().equals("Product 2")));

        verify(getProductsByPortalUseCase).execute(any(GetProductsByPortalInput.class));
        verifyNoMoreInteractions(getProductsByPortalUseCase);
    }

    @Test
    void testGetProducts_NoContent() {
        var portalId = 1L;
        var emptyProductSet = new HashSet<ProductIdAndName>();

        var output = new GetProductsByPortalOutput(emptyProductSet);
        when(getProductsByPortalUseCase.execute(any(GetProductsByPortalInput.class)))
                .thenReturn(output);

        var response = portalController.getProducts(portalId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        verify(getProductsByPortalUseCase).execute(any(GetProductsByPortalInput.class));
        verifyNoMoreInteractions(getProductsByPortalUseCase);
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

        var response = portalController.getProductDetails(productId);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(productDetails, response.getBody());
    }

}

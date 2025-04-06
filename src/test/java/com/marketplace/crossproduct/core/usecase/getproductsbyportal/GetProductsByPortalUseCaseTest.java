package com.marketplace.crossproduct.core.usecase.getproductsbyportal;

import com.marketplace.crossproduct.core.model.Product;
import com.marketplace.crossproduct.core.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class GetProductsByPortalUseCaseTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private GetProductsByPortalUseCase getProductsByPortalUseCase;

    @Test
    void testExecute_success() {
        var portalId = 1L;
        var productSet = new HashSet<Product>();
        productSet.add(new Product(1L, "Product 1", null));
        productSet.add(new Product(2L, "Product 2", null));

        when(productService.getByPortalId(portalId)).thenReturn(productSet);

        var input = GetProductsByPortalInput.builder().portalId(portalId).build();
        var output = getProductsByPortalUseCase.execute(input);

        assertNotNull(output);
        assertEquals(2, output.getProducts().size());
        assertTrue(output.getProducts().stream().anyMatch(product -> product.getName().equals("Product 1")));
        assertTrue(output.getProducts().stream().anyMatch(product -> product.getName().equals("Product 2")));

        verify(productService).getByPortalId(portalId);
        verifyNoMoreInteractions(productService);
    }

    @Test
    void testExecute_noProductsFound() {
        var portalId = 1L;
        var emptyProductSet = new HashSet<Product>();

        when(productService.getByPortalId(portalId)).thenReturn(emptyProductSet);

        var input = GetProductsByPortalInput.builder().portalId(portalId).build();
        var output = getProductsByPortalUseCase.execute(input);

        assertNotNull(output);
        assertTrue(output.getProducts().isEmpty());

        verify(productService).getByPortalId(portalId);
        verifyNoMoreInteractions(productService);
    }

}
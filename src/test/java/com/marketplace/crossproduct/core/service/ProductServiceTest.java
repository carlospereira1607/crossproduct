package com.marketplace.crossproduct.core.service;

import com.marketplace.crossproduct.core.model.Product;
import com.marketplace.crossproduct.core.port.db.ProductPortRepository;
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
class ProductServiceTest {

    @Mock
    private ProductPortRepository productPortRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void testGetByPortalId_success() {
        var portalId = 1L;
        var expectedProducts = new HashSet<Product>();
        expectedProducts.add(new Product(1L, "Product 1", null));
        expectedProducts.add(new Product(2L, "Product 2", null));

        when(productPortRepository.getByPortalId(portalId)).thenReturn(expectedProducts);

        var result = productService.getByPortalId(portalId);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(product -> product.getName().equals("Product 1")));
        assertTrue(result.stream().anyMatch(product -> product.getName().equals("Product 2")));

        verify(productPortRepository).getByPortalId(portalId);
        verifyNoMoreInteractions(productPortRepository);
    }

    @Test
    void testGetByPortalId_soProductsFound() {
        var portalId = 1L;
        var expectedProducts = new HashSet<Product>();

        when(productPortRepository.getByPortalId(portalId)).thenReturn(expectedProducts);

        var result = productService.getByPortalId(portalId);

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(productPortRepository).getByPortalId(portalId);
        verifyNoMoreInteractions(productPortRepository);
    }

}

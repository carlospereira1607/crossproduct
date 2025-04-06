package com.marketplace.crossproduct.outgoing.adapter.db;

import com.marketplace.crossproduct.core.model.AttributeValue;
import com.marketplace.crossproduct.core.model.Product;
import com.marketplace.crossproduct.outgoing.adapter.db.repository.ProductEntityRepository;
import com.marketplace.crossproduct.outgoing.adapter.db.repository.entity.AttributeValueEntity;
import com.marketplace.crossproduct.outgoing.adapter.db.repository.entity.ProductEntity;
import com.marketplace.crossproduct.outgoing.adapter.db.repository.mapper.ProductEntityMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class ProductPortRepositoryServiceTest {

    @Mock
    private ProductEntityRepository productEntityRepository;

    @Mock
    private ProductEntityMapper productEntityMapper;

    @InjectMocks
    private ProductPortRepositoryService productPortRepositoryService;

    @Test
    void testGetByPortalId_Success() {
        var portalId = 1L;
        var productEntity1 = new ProductEntity(1L, "Product 1", Collections.emptySet());
        var productEntity2 = new ProductEntity(2L, "Product 2", Collections.emptySet());
        var productEntities = new ArrayList<ProductEntity>();
        productEntities.add(productEntity1);
        productEntities.add(productEntity2);

        when(productEntityRepository.findAllByPortalId(portalId)).thenReturn(productEntities);

        var product1 = new Product(1L, "Product 1", Collections.emptySet());
        var product2 = new Product(2L, "Product 2", Collections.emptySet());
        when(productEntityMapper.toProduct(productEntity1)).thenReturn(product1);
        when(productEntityMapper.toProduct(productEntity2)).thenReturn(product2);

        var result = productPortRepositoryService.getByPortalId(portalId);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(product -> product.getName().equals("Product 1")));
        assertTrue(result.stream().anyMatch(product -> product.getName().equals("Product 2")));

        verify(productEntityRepository).findAllByPortalId(portalId);
        verify(productEntityMapper).toProduct(productEntity1);
        verify(productEntityMapper).toProduct(productEntity2);
    }

    @Test
    void testGetByPortalId_NoProductsFound() {
        var portalId = 1L;
        var productEntities = new ArrayList<ProductEntity>();

        when(productEntityRepository.findAllByPortalId(portalId)).thenReturn(productEntities);

        var result = productPortRepositoryService.getByPortalId(portalId);

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(productEntityRepository).findAllByPortalId(portalId);
        verifyNoInteractions(productEntityMapper);
    }

    @Test
    void testGetByPortalId_EmptyResult() {
        var portalId = 1L;

        when(productEntityRepository.findAllByPortalId(portalId)).thenReturn(Collections.emptyList());

        var result = productPortRepositoryService.getByPortalId(portalId);

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(productEntityRepository).findAllByPortalId(portalId);
        verifyNoInteractions(productEntityMapper);
    }

    @Test
    void testGetById_productFound() {
        var productId = 1L;
        var productEntity = new ProductEntity(productId, "Product A", Set.of(AttributeValueEntity.builder().build()));
        var product = new Product(productId, "Product A", Set.of(AttributeValue.builder().build()));

        when(productEntityRepository.findById(productId)).thenReturn(Optional.of(productEntity));
        when(productEntityMapper.toProduct(productEntity)).thenReturn(product);

        var result = productPortRepositoryService.getById(productId);

        assertTrue(result.isPresent(), "Product should be present");
        assertEquals(productId, result.get().getId(), "Product ID should match");
        assertEquals("Product A", result.get().getName(), "Product name should match");

        verify(productEntityRepository).findById(productId);
        verify(productEntityMapper).toProduct(productEntity);
        verifyNoMoreInteractions(productEntityRepository, productEntityMapper);
    }

    @Test
    void testGetById_productNotFound() {
        var productId = 1L;

        when(productEntityRepository.findById(productId)).thenReturn(Optional.empty());

        var result = productPortRepositoryService.getById(productId);

        assertFalse(result.isPresent(), "Product should not be present");

        verify(productEntityRepository).findById(productId);
        verifyNoMoreInteractions(productEntityRepository);
        verifyNoInteractions(productEntityMapper);
    }

}

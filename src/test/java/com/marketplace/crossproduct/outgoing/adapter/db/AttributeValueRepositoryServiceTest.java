package com.marketplace.crossproduct.outgoing.adapter.db;

import com.marketplace.crossproduct.core.model.AttributeDefinition;
import com.marketplace.crossproduct.core.model.AttributeValue;
import com.marketplace.crossproduct.core.model.Portal;
import com.marketplace.crossproduct.core.model.Product;
import com.marketplace.crossproduct.outgoing.adapter.db.repository.AttributeValueEntityRepository;
import com.marketplace.crossproduct.outgoing.adapter.db.repository.entity.AttributeValueEntity;
import com.marketplace.crossproduct.outgoing.adapter.db.repository.mapper.AttributeValueEntityMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class AttributeValueRepositoryServiceTest {

    @Mock
    private AttributeValueEntityRepository attributeValueEntityRepository;

    @Mock
    private AttributeValueEntityMapper attributeValueEntityMapper;

    @InjectMocks
    private AttributeValueRepositoryService repositoryService;

    private AttributeValue domainValue;
    private AttributeValueEntity entityValue;

    @BeforeEach
    void setUp() {
        var definition = AttributeDefinition.builder()
                .id(1L)
                .name("Color")
                .build();

        var product = Product.builder()
                .id(1L)
                .build();

        var portal = Portal.builder()
                .id(1L)
                .build();

        domainValue = AttributeValue.builder()
                .value("Red")
                .isStandard(true)
                .definition(definition)
                .product(product)
                .portal(portal)
                .build();

        entityValue = new AttributeValueEntity();
        entityValue.setValue("Red");
        entityValue.setIsStandard(true);
    }

    @Test
    void testSave_success() {
        when(attributeValueEntityMapper.toAttributeValueEntity(eq(domainValue), any())).thenReturn(entityValue);
        when(attributeValueEntityRepository.save(entityValue)).thenReturn(entityValue);
        when(attributeValueEntityMapper.toAttributeValue(entityValue)).thenReturn(domainValue);

        var result = repositoryService.save(domainValue);

        assertNotNull(result);
        assertEquals(domainValue.getIsStandard(), result.getIsStandard());
        assertEquals(domainValue.getValue(), result.getValue());

        verify(attributeValueEntityMapper).toAttributeValueEntity(eq(domainValue), any());
        verify(attributeValueEntityRepository).save(entityValue);
        verify(attributeValueEntityMapper).toAttributeValue(entityValue);
    }

}

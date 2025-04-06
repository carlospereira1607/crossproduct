package com.marketplace.crossproduct.outgoing.adapter.db;

import com.marketplace.crossproduct.core.model.AttributeDefinitionSpecification;
import com.marketplace.crossproduct.outgoing.adapter.db.repository.AttributeDefinitionSpecificationEntityRepository;
import com.marketplace.crossproduct.outgoing.adapter.db.repository.entity.AttributeDefinitionSpecificationEntity;
import com.marketplace.crossproduct.outgoing.adapter.db.repository.mapper.AttributeDefinitionSpecificationEntityMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class AttributeDefinitionSpecificationRepositoryServiceTest {

    @Mock
    private AttributeDefinitionSpecificationEntityRepository attributeDefinitionSpecificationEntityRepository;

    @Mock
    private AttributeDefinitionSpecificationEntityMapper attributeDefinitionSpecificationEntityMapper;

    @InjectMocks
    private AttributeDefinitionSpecificationRepositoryService attributeDefinitionSpecificationRepositoryService;

    @Test
    void testFindById_found() {
        var id = 1L;
        var attributeDefinitionSpecificationEntity = new AttributeDefinitionSpecificationEntity();
        var attributeDefinitionSpecification = new AttributeDefinitionSpecification();

        when(attributeDefinitionSpecificationEntityRepository.findById(id)).thenReturn(Optional.of(attributeDefinitionSpecificationEntity));
        when(attributeDefinitionSpecificationEntityMapper.toAttributeDefinitionSpecification(attributeDefinitionSpecificationEntity)).thenReturn(attributeDefinitionSpecification);

        var result = attributeDefinitionSpecificationRepositoryService.findById(id);

        assertTrue(result.isPresent());
        assertEquals(attributeDefinitionSpecification, result.get());
        verify(attributeDefinitionSpecificationEntityRepository).findById(id);
        verify(attributeDefinitionSpecificationEntityMapper).toAttributeDefinitionSpecification(attributeDefinitionSpecificationEntity);
        verifyNoMoreInteractions(attributeDefinitionSpecificationEntityMapper, attributeDefinitionSpecificationEntityRepository);
    }

    @Test
    void testFindById_notFound() {
        var id = 999L;
        when(attributeDefinitionSpecificationEntityRepository.findById(id)).thenReturn(Optional.empty());

        var result = attributeDefinitionSpecificationRepositoryService.findById(id);

        assertTrue(result.isEmpty());
        verify(attributeDefinitionSpecificationEntityRepository).findById(id);
        verifyNoMoreInteractions(attributeDefinitionSpecificationEntityRepository);
        verifyNoInteractions(attributeDefinitionSpecificationEntityMapper);
    }
}

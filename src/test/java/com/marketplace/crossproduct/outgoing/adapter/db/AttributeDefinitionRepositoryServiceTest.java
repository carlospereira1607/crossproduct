package com.marketplace.crossproduct.outgoing.adapter.db;

import com.marketplace.crossproduct.core.model.AttributeDefinition;
import com.marketplace.crossproduct.outgoing.adapter.db.repository.AttributeDefinitionEntityRepository;
import com.marketplace.crossproduct.outgoing.adapter.db.repository.entity.AttributeDefinitionEntity;
import com.marketplace.crossproduct.outgoing.adapter.db.repository.mapper.AttributeDefinitionEntityMapper;
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
class AttributeDefinitionRepositoryServiceTest {

    @Mock
    private AttributeDefinitionEntityRepository attributeDefinitionEntityRepository;

    @Mock
    private AttributeDefinitionEntityMapper attributeDefinitionEntityMapper;

    @InjectMocks
    private AttributeDefinitionRepositoryService attributeDefinitionRepositoryService;

    @Test
    void testSave_success() {
        var attributeDefinition = new AttributeDefinition();
        var attributeDefinitionEntity = new AttributeDefinitionEntity();
        var savedAttributeDefinitionEntity = new AttributeDefinitionEntity();
        var expectedResult = new AttributeDefinition();

        when(attributeDefinitionEntityMapper.toAttributeDefinitionEntity(attributeDefinition)).thenReturn(attributeDefinitionEntity);
        when(attributeDefinitionEntityRepository.save(attributeDefinitionEntity)).thenReturn(savedAttributeDefinitionEntity);
        when(attributeDefinitionEntityMapper.toAttributeDefinition(savedAttributeDefinitionEntity)).thenReturn(expectedResult);

        var result = attributeDefinitionRepositoryService.save(attributeDefinition);

        assertEquals(expectedResult, result);
        verify(attributeDefinitionEntityMapper).toAttributeDefinitionEntity(attributeDefinition);
        verify(attributeDefinitionEntityRepository).save(attributeDefinitionEntity);
        verify(attributeDefinitionEntityMapper).toAttributeDefinition(savedAttributeDefinitionEntity);

        verifyNoMoreInteractions(attributeDefinitionEntityMapper, attributeDefinitionEntityRepository);
    }

    @Test
    void testFindByPortalIdAndName_found() {
        var portalId = 1L;
        var name = "Color";

        var attributeDefinitionEntity = new AttributeDefinitionEntity();
        var attributeDefinition = new AttributeDefinition();

        when(attributeDefinitionEntityRepository.findByNameAndPortalId(name, portalId)).thenReturn(Optional.of(attributeDefinitionEntity));
        when(attributeDefinitionEntityMapper.toAttributeDefinition(attributeDefinitionEntity)).thenReturn(attributeDefinition);

        var result = attributeDefinitionRepositoryService.findByPortalIdAndName(portalId, name);

        assertTrue(result.isPresent());
        assertEquals(attributeDefinition, result.get());
        verify(attributeDefinitionEntityRepository).findByNameAndPortalId(name, portalId);
        verify(attributeDefinitionEntityMapper).toAttributeDefinition(attributeDefinitionEntity);
        verifyNoMoreInteractions(attributeDefinitionEntityRepository, attributeDefinitionEntityMapper);
    }

    @Test
    void testFindByPortalIdAndName_notFound() {
        var portalId = 1L;
        var name = "NonExistent";

        when(attributeDefinitionEntityRepository.findByNameAndPortalId(name, portalId)).thenReturn(Optional.empty());

        var result = attributeDefinitionRepositoryService.findByPortalIdAndName(portalId, name);

        assertTrue(result.isEmpty());
        verify(attributeDefinitionEntityRepository).findByNameAndPortalId(name, portalId);
        verifyNoMoreInteractions(attributeDefinitionEntityRepository);
        verifyNoInteractions(attributeDefinitionEntityMapper);
    }

}

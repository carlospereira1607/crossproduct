package com.marketplace.crossproduct.outgoing.adapter.db;

import com.marketplace.crossproduct.core.model.AttributeDefinition;
import com.marketplace.crossproduct.core.model.AttributeDefinitionSpecificationType;
import com.marketplace.crossproduct.core.model.AttributeDefinitionType;
import com.marketplace.crossproduct.outgoing.adapter.db.repository.AttributeDefinitionEntityRepository;
import com.marketplace.crossproduct.outgoing.adapter.db.repository.entity.AttributeDefinitionEntity;
import com.marketplace.crossproduct.outgoing.adapter.db.repository.mapper.AttributeDefinitionEntityMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class AttributeDefinitionRepositoryServiceTest {

    @Mock
    private AttributeDefinitionEntityRepository repository;

    @Mock
    private AttributeDefinitionEntityMapper attributeDefinitionEntityMapper;

    @InjectMocks
    private AttributeDefinitionRepositoryService attributeDefinitionRepositoryService;

    private AttributeDefinition attributeDefinition;
    private AttributeDefinitionEntity attributeDefinitionEntity;

    @BeforeEach
    void setUp() {
        attributeDefinition = AttributeDefinition.builder()
                .id(1L)
                .name("Test Attribute")
                .definitionType(AttributeDefinitionType.TEXT)
                .specificationType(AttributeDefinitionSpecificationType.TEXT_FORMAT)
                .specificationValue("Test Value")
                .selectableOptions(new HashSet<>(Arrays.asList("option1", "option2")))
                .build();

        attributeDefinitionEntity = AttributeDefinitionEntity.builder()
                .id(1L)
                .name("Test Attribute")
                .definitionType(AttributeDefinitionType.TEXT)
                .specificationType(AttributeDefinitionSpecificationType.TEXT_FORMAT)
                .specificationValue("Test Value")
                .selectableOptions(new HashSet<>(Arrays.asList("option1", "option2")))
                .build();
    }

    @Test
    void testSave() {
        when(attributeDefinitionEntityMapper.toAttributeDefinitionEntity(attributeDefinition)).thenReturn(attributeDefinitionEntity);
        when(repository.save(attributeDefinitionEntity)).thenReturn(attributeDefinitionEntity);
        when(attributeDefinitionEntityMapper.toAttributeDefinition(attributeDefinitionEntity)).thenReturn(attributeDefinition);

        var savedDefinition = attributeDefinitionRepositoryService.save(attributeDefinition);

        assertNotNull(savedDefinition);
        assertEquals(attributeDefinition.getId(), savedDefinition.getId());
        assertEquals(attributeDefinition.getName(), savedDefinition.getName());
        verify(repository).save(attributeDefinitionEntity);
    }

    @Test
    void testFindByNameAndTypeAndSpecificationIdAndSelectableOptions() {
        when(repository.findByNameAndTypeAndSpecificationTypeAndValueAndSelectableOptions(
                any(), any(), any(), any(), any())).thenReturn(Optional.of(attributeDefinitionEntity));
        when(attributeDefinitionEntityMapper.toAttributeDefinition(attributeDefinitionEntity)).thenReturn(attributeDefinition);

        var result = attributeDefinitionRepositoryService
                .findByNameAndTypeAndSpecificationIdAndSelectableOptions("Test Attribute", AttributeDefinitionType.TEXT,
                        AttributeDefinitionSpecificationType.TEXT_FORMAT, "Test Value", new HashSet<>(Arrays.asList("option1", "option2")));

        assertTrue(result.isPresent());
        assertEquals(attributeDefinition.getId(), result.get().getId());
        assertEquals(attributeDefinition.getName(), result.get().getName());
        verify(repository).findByNameAndTypeAndSpecificationTypeAndValueAndSelectableOptions(
                any(), any(), any(), any(), any());
    }

    @Test
    void testFindById() {
        when(repository.findById(1L)).thenReturn(Optional.of(attributeDefinitionEntity));
        when(attributeDefinitionEntityMapper.toAttributeDefinition(attributeDefinitionEntity)).thenReturn(attributeDefinition);

        var result = attributeDefinitionRepositoryService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(attributeDefinition.getId(), result.get().getId());
        assertEquals(attributeDefinition.getName(), result.get().getName());
        verify(repository).findById(1L);
    }

    @Test
    void testFindByIdWhenNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        var result = attributeDefinitionRepositoryService.findById(1L);

        assertTrue(result.isEmpty());
        verify(repository).findById(1L);
    }

}

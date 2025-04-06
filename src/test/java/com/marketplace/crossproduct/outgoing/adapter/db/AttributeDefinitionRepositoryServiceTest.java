package com.marketplace.crossproduct.outgoing.adapter.db;

import com.marketplace.crossproduct.core.model.AttributeDefinition;
import com.marketplace.crossproduct.core.model.AttributeDefinitionType;
import com.marketplace.crossproduct.outgoing.adapter.db.repository.AttributeDefinitionEntityRepository;
import com.marketplace.crossproduct.outgoing.adapter.db.repository.entity.AttributeDefinitionEntity;
import com.marketplace.crossproduct.outgoing.adapter.db.repository.mapper.AttributeDefinitionEntityMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class AttributeDefinitionRepositoryServiceTest {

    @Mock
    private AttributeDefinitionEntityRepository repository;

    @Mock
    private AttributeDefinitionEntityMapper mapper;

    @InjectMocks
    private AttributeDefinitionRepositoryService service;

    @Test
    void save_shouldPersistAndReturnMappedDomainObject() {
        var domain = new AttributeDefinition(); // Replace with your builder/init
        var entity = new AttributeDefinitionEntity();
        var savedEntity = new AttributeDefinitionEntity();
        var mappedResult = new AttributeDefinition();

        when(mapper.toAttributeDefinitionEntity(domain)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(savedEntity);
        when(mapper.toAttributeDefinition(savedEntity)).thenReturn(mappedResult);

        var result = service.save(domain);

        assertEquals(mappedResult, result);
        verify(mapper).toAttributeDefinitionEntity(domain);
        verify(repository).save(entity);
        verify(mapper).toAttributeDefinition(savedEntity);
    }

    @Test
    void findById_shouldReturnMappedDomainIfPresent() {
        var id = 1L;
        var entity = new AttributeDefinitionEntity();
        var domain = new AttributeDefinition();

        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toAttributeDefinition(entity)).thenReturn(domain);

        var result = service.findById(id);

        assertTrue(result.isPresent());
        assertEquals(domain, result.get());
        verify(repository).findById(id);
        verify(mapper).toAttributeDefinition(entity);
    }

    @Test
    void findByNameAndTypeAndSpecificationIdAndSelectableOptions_shouldReturnMappedDomainIfPresent() {
        var name = "Color";
        var type = "TEXT";
        var specId = 1L;
        var options = Set.of("Red", "Green");

        var entity = new AttributeDefinitionEntity();
        var domain = new AttributeDefinition();

        when(repository.findByNameAndTypeAndSpecificationIdAndSelectableOptions(
                eq(name), eq(AttributeDefinitionType.valueOf(type)), eq(specId), eq(options)))
                .thenReturn(Optional.of(entity));

        when(mapper.toAttributeDefinition(entity)).thenReturn(domain);

        var result = service.findByNameAndTypeAndSpecificationIdAndSelectableOptions(name, type, specId, options);

        assertTrue(result.isPresent());
        assertEquals(domain, result.get());
        verify(repository).findByNameAndTypeAndSpecificationIdAndSelectableOptions(
                name, AttributeDefinitionType.valueOf(type), specId, options);
        verify(mapper).toAttributeDefinition(entity);
    }

    @Test
    void findById_shouldReturnEmptyIfNotFound() {
        var id = 404L;

        when(repository.findById(id)).thenReturn(Optional.empty());

        var result = service.findById(id);

        assertTrue(result.isEmpty());
        verify(repository).findById(id);
        verifyNoInteractions(mapper);
    }

    @Test
    void findByNameAndTypeAndSpecificationIdAndSelectableOptions_shouldReturnEmptyIfNotFound() {
        var name = "Color";
        var type = "TEXT";
        var specId = 1L;
        var options = Set.of("Blue");

        when(repository.findByNameAndTypeAndSpecificationIdAndSelectableOptions(
                eq(name), eq(AttributeDefinitionType.valueOf(type)), eq(specId), eq(options)))
                .thenReturn(Optional.empty());

        var result = service.findByNameAndTypeAndSpecificationIdAndSelectableOptions(name, type, specId, options);

        assertTrue(result.isEmpty());
        verifyNoInteractions(mapper);
    }

}

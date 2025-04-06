package com.marketplace.crossproduct.core.service;

import com.marketplace.crossproduct.core.model.AttributeDefinition;
import com.marketplace.crossproduct.core.model.AttributeDefinitionSpecificationType;
import com.marketplace.crossproduct.core.model.AttributeDefinitionType;
import com.marketplace.crossproduct.core.port.db.AttributeDefinitionPortRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class AttributeDefinitionServiceTest {

    @Mock
    private AttributeDefinitionPortRepository attributeDefinitionPortRepository;

    @InjectMocks
    private AttributeDefinitionService attributeDefinitionService;

    private AttributeDefinition attributeDefinition;
    private Set<String> selectableOptions;

    @BeforeEach
    void setUp() {
        selectableOptions = new HashSet<>(Arrays.asList("option1", "option2"));
        attributeDefinition = AttributeDefinition.builder()
                .name("Test Attribute")
                .definitionType(AttributeDefinitionType.TEXT)
                .specificationType(AttributeDefinitionSpecificationType.TEXT_FORMAT)
                .specificationValue("Test Value")
                .selectableOptions(selectableOptions)
                .build();
    }

    @Test
    void testSave() {
        when(attributeDefinitionPortRepository.save(any(AttributeDefinition.class))).thenReturn(attributeDefinition);

        var savedDefinition = attributeDefinitionService.save(
                "Test Attribute", AttributeDefinitionType.TEXT, AttributeDefinitionSpecificationType.TEXT_FORMAT,
                "Test Value", selectableOptions
        );

        assertNotNull(savedDefinition);
        assertEquals("Test Attribute", savedDefinition.getName());
        assertEquals(AttributeDefinitionType.TEXT, savedDefinition.getDefinitionType());
        verify(attributeDefinitionPortRepository).save(any(AttributeDefinition.class));
    }

    @Test
    void testFindByNameAndTypeAndSpecificationIdAndSelectableOptions() {
        when(attributeDefinitionPortRepository.findByNameAndTypeAndSpecificationIdAndSelectableOptions(
                any(), any(), any(), any(), any()))
                .thenReturn(Optional.of(attributeDefinition));

        var foundDefinition = attributeDefinitionService.findByNameAndTypeAndSpecificationIdAndSelectableOptions(
                "Test Attribute", AttributeDefinitionType.TEXT, AttributeDefinitionSpecificationType.TEXT_FORMAT,
                "Test Value", selectableOptions
        );

        assertTrue(foundDefinition.isPresent());
        assertEquals("Test Attribute", foundDefinition.get().getName());
        verify(attributeDefinitionPortRepository).findByNameAndTypeAndSpecificationIdAndSelectableOptions(
                any(), any(), any(), any(), any());
    }

    @Test
    void testFindById() {
        when(attributeDefinitionPortRepository.findById(any())).thenReturn(Optional.of(attributeDefinition));

        var foundDefinition = attributeDefinitionService.findById(1L);

        assertTrue(foundDefinition.isPresent());
        assertEquals("Test Attribute", foundDefinition.get().getName());
        verify(attributeDefinitionPortRepository).findById(any());
    }

    @Test
    void testFindById_NotFound() {
        when(attributeDefinitionPortRepository.findById(any())).thenReturn(Optional.empty());

        var foundDefinition = attributeDefinitionService.findById(1L);

        assertTrue(foundDefinition.isEmpty());
        verify(attributeDefinitionPortRepository).findById(any());
    }

}

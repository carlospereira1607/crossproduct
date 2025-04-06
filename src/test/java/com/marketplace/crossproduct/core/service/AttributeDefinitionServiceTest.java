package com.marketplace.crossproduct.core.service;

import com.marketplace.crossproduct.core.model.AttributeDefinition;
import com.marketplace.crossproduct.core.model.AttributeDefinitionSpecification;
import com.marketplace.crossproduct.core.model.AttributeDefinitionType;
import com.marketplace.crossproduct.core.port.db.AttributeDefinitionPortRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class AttributeDefinitionServiceTest {

    @Mock
    private AttributeDefinitionPortRepository attributeDefinitionPortRepository;

    @InjectMocks
    private AttributeDefinitionService attributeDefinitionService;

    private AttributeDefinitionSpecification specification;

    @BeforeEach
    void setup() {
        specification = new AttributeDefinitionSpecification();
    }

    @Test
    void testSave_shouldReturnSavedAttributeDefinition() {
        var name = "color";
        var type = AttributeDefinitionType.TEXT;
        var options = Set.of("red", "blue");

        var attributeToSave = AttributeDefinition.builder()
                .name(name)
                .type(type)
                .specification(specification)
                .selectableOptions(options)
                .build();

        when(attributeDefinitionPortRepository.save(any(AttributeDefinition.class)))
                .thenReturn(attributeToSave);

        var result = attributeDefinitionService.save(name, type, specification, options);

        assertNotNull(result);
        assertEquals(name, result.getName());
        assertEquals(type, result.getType());
        assertEquals(options, result.getSelectableOptions());

        verify(attributeDefinitionPortRepository).save(any(AttributeDefinition.class));
        verifyNoMoreInteractions(attributeDefinitionPortRepository);
    }

    @Test
    void testFindByPortalIdAndName_whenFound() {
        var portalId = 1L;
        var name = "size";
        var expected = AttributeDefinition.builder().name(name).build();

        when(attributeDefinitionPortRepository.findByPortalIdAndName(portalId, name))
                .thenReturn(Optional.of(expected));

        Optional<AttributeDefinition> result = attributeDefinitionService.findByPortalIdAndName(portalId, name);

        assertTrue(result.isPresent());
        assertEquals(name, result.get().getName());

        verify(attributeDefinitionPortRepository).findByPortalIdAndName(portalId, name);
        verifyNoMoreInteractions(attributeDefinitionPortRepository);
    }

    @Test
    void testFindByPortalIdAndName_whenNotFound() {
        var portalId = 2L;
        var name = "nonexistent";

        when(attributeDefinitionPortRepository.findByPortalIdAndName(portalId, name))
                .thenReturn(Optional.empty());

        var result = attributeDefinitionService.findByPortalIdAndName(portalId, name);

        assertTrue(result.isEmpty());

        verify(attributeDefinitionPortRepository).findByPortalIdAndName(portalId, name);
        verifyNoMoreInteractions(attributeDefinitionPortRepository);
    }

}

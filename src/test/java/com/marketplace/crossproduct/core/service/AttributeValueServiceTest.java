package com.marketplace.crossproduct.core.service;

import com.marketplace.crossproduct.core.model.AttributeDefinition;
import com.marketplace.crossproduct.core.model.AttributeValue;
import com.marketplace.crossproduct.core.port.db.AttributeValuePortRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class AttributeValueServiceTest {

    @Mock
    private AttributeValuePortRepository attributeValuePortRepository;

    @InjectMocks
    private AttributeValueService attributeValueService;

    @Test
    void testFindById_existingId_returnsValue() {
        var id = 1L;
        AttributeValue value = AttributeValue.builder()
                .id(id)
                .value("Color")
                .isStandard(false)
                .definition(AttributeDefinition.builder().id(2L).build())
                .build();

        when(attributeValuePortRepository.findById(id)).thenReturn(Optional.of(value));

        var result = attributeValueService.findById(id);

        assertTrue(result.isPresent());
        assertEquals(value, result.get());

        verify(attributeValuePortRepository).findById(id);
        verifyNoMoreInteractions(attributeValuePortRepository);
    }

    @Test
    void testFindById_nonExistingId_returnsEmptyOptional() {
        var id = 999L;
        when(attributeValuePortRepository.findById(id)).thenReturn(Optional.empty());

        var result = attributeValueService.findById(id);

        assertTrue(result.isEmpty());
        verify(attributeValuePortRepository).findById(id);
        verifyNoMoreInteractions(attributeValuePortRepository);
    }

    @Test
    void testUpdate_validValue_returnsUpdatedValue() {
        AttributeValue inputValue = AttributeValue.builder()
                .id(1L)
                .value("Updated")
                .isStandard(true)
                .definition(AttributeDefinition.builder().id(2L).build())
                .build();

        when(attributeValuePortRepository.save(inputValue)).thenReturn(inputValue);

        var result = attributeValueService.update(inputValue);

        assertNotNull(result);
        assertEquals(inputValue, result);

        verify(attributeValuePortRepository).save(inputValue);
        verifyNoMoreInteractions(attributeValuePortRepository);
    }

    @Test
    void testCreate_success() {
        var definition = AttributeDefinition.builder()
                .id(1L)
                .name("Color")
                .build();

        var inputValue = "Red";
        var isStandard = true;

        var expectedAttributeValue = AttributeValue.builder()
                .id(100L)
                .definition(definition)
                .value(inputValue)
                .isStandard(isStandard)
                .build();

        when(attributeValuePortRepository.save(any(AttributeValue.class)))
                .thenReturn(expectedAttributeValue);

        var result = attributeValueService.create(definition, inputValue, isStandard);

        assertNotNull(result);
        assertEquals(expectedAttributeValue.getId(), result.getId());
        assertEquals(expectedAttributeValue.getValue(), result.getValue());
        assertEquals(expectedAttributeValue.getDefinition(), result.getDefinition());
        assertTrue(result.getIsStandard());

        verify(attributeValuePortRepository).save(any(AttributeValue.class));
        verifyNoMoreInteractions(attributeValuePortRepository);
    }

}

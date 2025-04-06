package com.marketplace.crossproduct.core.service;

import com.marketplace.crossproduct.core.model.AttributeDefinition;
import com.marketplace.crossproduct.core.model.AttributeValue;
import com.marketplace.crossproduct.core.model.Portal;
import com.marketplace.crossproduct.core.model.Product;
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
    void testFindById_existingPortalProductDefinition_returnsValue() {
        var value = AttributeValue.builder()
                .value("Color")
                .isStandard(false)
                .definition(AttributeDefinition.builder().id(2L).build())
                .build();

        when(attributeValuePortRepository.findByPortalProductDefinition(any(), any(), any())).thenReturn(Optional.of(value));

        var result = attributeValueService.findByPortalProductDefinition(any(), any(), any());

        assertTrue(result.isPresent());
        assertEquals(value, result.get());

        verify(attributeValuePortRepository).findByPortalProductDefinition(any(), any(), any());
        verifyNoMoreInteractions(attributeValuePortRepository);
    }

    @Test
    void testFindById_nonExistingPortalProductDefinition_returnsEmptyOptional() {
        when(attributeValuePortRepository.findByPortalProductDefinition(any(), any(), any())).thenReturn(Optional.empty());

        var result = attributeValueService.findByPortalProductDefinition(any(), any(), any());

        assertTrue(result.isEmpty());
        verify(attributeValuePortRepository).findByPortalProductDefinition(any(), any(), any());
        verifyNoMoreInteractions(attributeValuePortRepository);
    }

    @Test
    void testUpdate_validValue_returnsUpdatedValue() {
        var inputValue = AttributeValue.builder()
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

        var portal = Portal.builder().build();
        var product = Product.builder().build();

        var inputValue = "Red";
        var isStandard = true;

        var expectedAttributeValue = AttributeValue.builder()
                .definition(definition)
                .value(inputValue)
                .isStandard(isStandard)
                .build();

        when(attributeValuePortRepository.save(any(AttributeValue.class))).thenReturn(expectedAttributeValue);

        var result = attributeValueService.create(definition, product, portal, inputValue, isStandard);

        assertNotNull(result);
        assertEquals(expectedAttributeValue.getIsStandard(), result.getIsStandard());
        assertEquals(expectedAttributeValue.getValue(), result.getValue());
        assertEquals(expectedAttributeValue.getDefinition(), result.getDefinition());
        assertTrue(result.getIsStandard());

        verify(attributeValuePortRepository).save(any(AttributeValue.class));
        verifyNoMoreInteractions(attributeValuePortRepository);
    }

}

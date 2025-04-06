package com.marketplace.crossproduct.core.service;

import com.marketplace.crossproduct.core.model.AttributeDefinitionSpecification;
import com.marketplace.crossproduct.core.port.db.AttributeDefinitionSpecificationPortRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class AttributeDefinitionSpecificationServiceTest {

    @Mock
    private AttributeDefinitionSpecificationPortRepository specificationPortRepository;

    @InjectMocks
    private AttributeDefinitionSpecificationService specificationService;

    @Test
    void testFindById_whenFound() {
        var id = 1L;
        var expectedSpec = new AttributeDefinitionSpecification(); // or mock it
        when(specificationPortRepository.findById(id)).thenReturn(Optional.of(expectedSpec));

        var result = specificationService.findById(id);

        assertTrue(result.isPresent());
        assertEquals(expectedSpec, result.get());
        verify(specificationPortRepository).findById(id);
        verifyNoMoreInteractions(specificationPortRepository);
    }

    @Test
    void testFindById_whenNotFound() {
        var id = 99L;
        when(specificationPortRepository.findById(id)).thenReturn(Optional.empty());

        var result = specificationService.findById(id);

        assertTrue(result.isEmpty());
        verify(specificationPortRepository).findById(id);
        verifyNoMoreInteractions(specificationPortRepository);
    }

}

package com.marketplace.crossproduct.core.service;

import com.marketplace.crossproduct.core.model.Portal;
import com.marketplace.crossproduct.core.port.db.PortalPortRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class PortalServiceTest {

    @Mock
    private PortalPortRepository portalPortRepository;

    @InjectMocks
    private PortalService portalService;

    @Test
    void testGetById_found() {
        var expectedPortal = Portal.builder().id(1L).name("Test Portal").build();
        when(portalPortRepository.findById(expectedPortal.getId())).thenReturn(Optional.of(expectedPortal));

        var existingPortal = portalService.getById(1L);

        assertTrue(existingPortal.isPresent());
        assertEquals(expectedPortal, existingPortal.get());

        verify(portalPortRepository).findById(expectedPortal.getId());
        verifyNoMoreInteractions(portalPortRepository);
    }

    @Test
    void testGetById_notFound() {
        when(portalPortRepository.findById(999L)).thenReturn(Optional.empty());

        var foundPortal = portalService.getById(999L);

        assertTrue(foundPortal.isEmpty());

        verify(portalPortRepository).findById(999L);
    }

}

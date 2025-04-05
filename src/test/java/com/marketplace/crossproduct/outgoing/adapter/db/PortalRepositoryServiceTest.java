package com.marketplace.crossproduct.outgoing.adapter.db;

import com.marketplace.crossproduct.core.model.Portal;
import com.marketplace.crossproduct.outgoing.adapter.db.repository.PortalEntityRepository;
import com.marketplace.crossproduct.outgoing.adapter.db.repository.entity.PortalEntity;
import com.marketplace.crossproduct.outgoing.adapter.db.repository.mapper.PortalEntityMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class PortalRepositoryServiceTest {

    @InjectMocks
    private PortalRepositoryService portalRepositoryService;

    @Mock
    private PortalEntityRepository portalEntityRepository;

    @Mock
    private PortalEntityMapper portalEntityMapper;

    @Test
    void testFindById_found() {

        var portalEntity = PortalEntity.builder().id(1L).name("Test Portal").build();
        var portal = Portal.builder().id(1L).name("Test Portal").build();

        when(portalEntityRepository.findById(any())).thenReturn(Optional.of(portalEntity));
        when(portalEntityMapper.toPortal(portalEntity)).thenReturn(portal);

        var foundPortal = portalRepositoryService.findById(1L);

        assertTrue(foundPortal.isPresent());
        assertEquals(1L, foundPortal.get().getId());
        assertEquals("Test Portal", foundPortal.get().getName());

        verify(portalEntityRepository).findById(1L);
        verify(portalEntityMapper).toPortal(any(PortalEntity.class));

        verifyNoMoreInteractions(portalEntityRepository, portalEntityMapper);
    }

    @Test
    void testFindById_notFound() {
        var foundPortal = portalRepositoryService.findById(999L);

        assertFalse(foundPortal.isPresent());

        verify(portalEntityRepository).findById(999L);
        verifyNoInteractions(portalEntityMapper);
    }

}

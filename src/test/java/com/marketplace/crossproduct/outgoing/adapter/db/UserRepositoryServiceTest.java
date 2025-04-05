package com.marketplace.crossproduct.outgoing.adapter.db;

import com.marketplace.crossproduct.core.model.Portal;
import com.marketplace.crossproduct.core.model.User;
import com.marketplace.crossproduct.outgoing.adapter.db.repository.UserEntityRepository;
import com.marketplace.crossproduct.outgoing.adapter.db.repository.entity.PortalEntity;
import com.marketplace.crossproduct.outgoing.adapter.db.repository.entity.UserEntity;
import com.marketplace.crossproduct.outgoing.adapter.db.repository.mapper.UserEntityMapperImpl;
import com.marketplace.crossproduct.security.Role;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class UserRepositoryServiceTest {

    @InjectMocks
    private UserRepositoryService userRepositoryService;

    @Mock
    private UserEntityRepository userEntityRepository;

    @Mock
    private UserEntityMapperImpl userEntityMapper;

    PortalEntity portalEntity = PortalEntity.builder()
            .id(1L)
            .name("Some name")
            .build();

    @Test
    void testSaveUser_successful() {
        var user = User.builder()
                .username("username")
                .password("password")
                .role(Role.PORTAL_ADMIN)
                .portal(Portal.builder().build())
                .build();

        var userEntity = UserEntity.builder()
                .id(1L)
                .username("username")
                .password("password")
                .role(Role.PORTAL_ADMIN)
                .portal(portalEntity)
                .build();

        when(userEntityMapper.toUserEntity(user)).thenReturn(userEntity);
        when(userEntityRepository.save(userEntity)).thenReturn(userEntity);
        when(userEntityMapper.toUser(userEntity)).thenReturn(user);

        var savedUser = userRepositoryService.save(user);

        assertNotNull(savedUser);
        assertEquals("username", savedUser.getUsername());
        verify(userEntityRepository).save(userEntity);
        verify(userEntityMapper).toUserEntity(user);
        verify(userEntityMapper).toUser(userEntity);

        verifyNoMoreInteractions(userEntityRepository, userEntityMapper);
    }

    @Test
    void testFindByUsername_successful() {
        var user = User.builder()
                .username("username")
                .password("password")
                .role(Role.PORTAL_ADMIN)
                .portal(Portal.builder().build())
                .build();

        var userEntity = UserEntity.builder()
                .id(1L)
                .username("username")
                .password("password")
                .role(Role.PORTAL_ADMIN)
                .portal(portalEntity)
                .build();

        when(userEntityRepository.findByUsername("username")).thenReturn(Optional.of(userEntity));
        when(userEntityMapper.toUser(userEntity)).thenReturn(user);

        var foundUser = userRepositoryService.findByUsername("username");

        assertTrue(foundUser.isPresent());
        assertEquals("username", foundUser.get().getUsername());
        verify(userEntityRepository).findByUsername("username");
        verify(userEntityMapper).toUser(userEntity);

        verifyNoMoreInteractions(userEntityRepository, userEntityMapper);
    }

    @Test
    void testFindByUsername_notFound() {
        when(userEntityRepository.findByUsername("nonexistent")).thenReturn(Optional.empty());

        var existing = userRepositoryService.findByUsername("nonexistent");

        assertTrue(existing.isEmpty());
        verify(userEntityRepository).findByUsername("nonexistent");

        verifyNoMoreInteractions(userEntityRepository);
        verifyNoInteractions(userEntityMapper);
    }

}
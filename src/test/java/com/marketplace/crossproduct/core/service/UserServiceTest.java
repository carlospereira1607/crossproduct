package com.marketplace.crossproduct.core.service;

import com.marketplace.crossproduct.core.model.User;
import com.marketplace.crossproduct.core.port.db.UserPortRepository;
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
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserPortRepository userPortRepository;

    @Test
    void testGetByUsername_found() {
        var user = User.builder()
                .id(1L)
                .username("testuser")
                .password("somepassword")
                .role(Role.PORTAL_ADMIN)
                .build();

        when(userPortRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        var existingUser = userService.getByUsername(user.getUsername());

        assertTrue(existingUser.isPresent());
        assertEquals(user, existingUser.get());

        verify(userPortRepository).findByUsername(user.getUsername());
        verifyNoMoreInteractions(userPortRepository);
    }

    @Test
    void testGetByUsername_notFound() {
        when(userPortRepository.findByUsername("nonexistentuser")).thenReturn(Optional.empty());

        var foundUser = userService.getByUsername("nonexistentuser");

        assertTrue(foundUser.isEmpty());

        verify(userPortRepository).findByUsername("nonexistentuser");
        verifyNoMoreInteractions(userPortRepository);
    }

    @Test
    void testSave() {
        var user = User.builder()
                .id(1L)
                .username("testuser")
                .password("somepassword")
                .role(Role.PORTAL_ADMIN)
                .build();

        when(userPortRepository.save(user)).thenReturn(user);

        var savedUser = userService.save(user);

        assertNotNull(savedUser);
        assertEquals(user, savedUser);

        verify(userPortRepository).save(user);
        verifyNoMoreInteractions(userPortRepository);
    }

}

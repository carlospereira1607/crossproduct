package com.marketplace.crossproduct.incoming.rest;

import com.marketplace.crossproduct.core.usecase.auth.AuthenticateUserInput;
import com.marketplace.crossproduct.core.usecase.auth.AuthenticateUserOutput;
import com.marketplace.crossproduct.core.usecase.auth.AuthenticateUserUseCase;
import com.marketplace.crossproduct.core.usecase.createuser.CreateUserInput;
import com.marketplace.crossproduct.core.usecase.createuser.CreateUserOutput;
import com.marketplace.crossproduct.core.usecase.createuser.CreateUserUseCase;
import com.marketplace.crossproduct.incoming.dto.CreateUserRequestDto;
import com.marketplace.crossproduct.incoming.dto.CreateUserResponseDto;
import com.marketplace.crossproduct.incoming.dto.LoginRequestDto;
import com.marketplace.crossproduct.security.Role;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class AuthControllerTest {

    @Mock
    private CreateUserUseCase createUserUseCase;

    @Mock
    private AuthenticateUserUseCase authenticateUserUseCase;

    @InjectMocks
    private AuthController authController;

    @Test
    void testCreateUser_success() {
        var requestDto = new CreateUserRequestDto("testuser", "USER", "PORTAL_ADMIN", 1L);

        var expectedOutput = CreateUserOutput
                .builder()
                .id(1L)
                .username(requestDto.username())
                .password(requestDto.password())
                .role(Role.valueOf(requestDto.role()))
                .portalId(requestDto.portalId())
                .build();

        var expectedResponse = new CreateUserResponseDto(expectedOutput.getId(), expectedOutput.getUsername(),
                expectedOutput.getPassword(), expectedOutput.getRole().name(), expectedOutput.getPortalId());

        when(createUserUseCase.execute(any(CreateUserInput.class))).thenReturn(expectedOutput);

        var response = authController.createUser(requestDto);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(expectedResponse, response.getBody());

        verify(createUserUseCase).execute(any(CreateUserInput.class));
        verifyNoMoreInteractions(createUserUseCase);
        verifyNoInteractions(authenticateUserUseCase);
    }

    @Test
    void testAuthenticateUser_success() {
        var loginRequestDto = new LoginRequestDto("testuser", "password123");

        var authenticateUserOutput = AuthenticateUserOutput.builder().token("someAuthToken").build();

        when(authenticateUserUseCase.execute(any(AuthenticateUserInput.class))).thenReturn(authenticateUserOutput);

        authController.authenticateUser(loginRequestDto);

        verify(authenticateUserUseCase).execute(any(AuthenticateUserInput.class));
        verifyNoMoreInteractions(authenticateUserUseCase);
        verifyNoInteractions(createUserUseCase);
    }
}

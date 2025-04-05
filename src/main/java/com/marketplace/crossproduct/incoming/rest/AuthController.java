package com.marketplace.crossproduct.incoming.rest;

import com.marketplace.crossproduct.core.usecase.auth.AuthenticateUserInput;
import com.marketplace.crossproduct.core.usecase.auth.AuthenticateUserUseCase;
import com.marketplace.crossproduct.core.usecase.createuser.CreateUserInput;
import com.marketplace.crossproduct.core.usecase.createuser.CreateUserUseCase;
import com.marketplace.crossproduct.incoming.dto.CreateUserRequestDto;
import com.marketplace.crossproduct.incoming.dto.CreateUserResponseDto;
import com.marketplace.crossproduct.incoming.dto.LoginRequestDto;
import com.marketplace.crossproduct.incoming.dto.LoginResponseDto;
import com.marketplace.crossproduct.security.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticateUserUseCase authenticateUserUseCase;
    private final CreateUserUseCase createUserUseCase;


    @PostMapping("/create")
    public ResponseEntity<CreateUserResponseDto> createUser(@RequestBody CreateUserRequestDto createUserRequestDto) {
        var createUserInput = CreateUserInput.builder()
                .username(createUserRequestDto.username())
                .role(Role.valueOf(createUserRequestDto.role())) //TODO
                .password(createUserRequestDto.password())
                .portalId(createUserRequestDto.portalId())
                .build();

        var result = createUserUseCase.execute(createUserInput);
        var responseDto = new CreateUserResponseDto(result.getId(), result.getUsername(), result.getPassword(),
                result.getRole().name(), result.getPortalId());

        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/login")
    public LoginResponseDto authenticateUser(@RequestBody LoginRequestDto loginRequestDto) {

        var authenticateUserInput = AuthenticateUserInput.builder()
                .username(loginRequestDto.username())
                .password(loginRequestDto.password())
                .build();

        var output = authenticateUserUseCase.execute(authenticateUserInput);

        return new LoginResponseDto(output.getToken());
    }


}

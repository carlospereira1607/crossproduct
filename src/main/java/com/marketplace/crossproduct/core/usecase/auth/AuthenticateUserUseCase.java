package com.marketplace.crossproduct.core.usecase.auth;

import com.marketplace.crossproduct.core.service.UserService;
import com.marketplace.crossproduct.core.usecase.UseCase;
import com.marketplace.crossproduct.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticateUserUseCase implements UseCase<AuthenticateUserInput, AuthenticateUserOutput> {

    private final UserService userService;
    private final JwtUtils jwtUtils;

    @Override
    public AuthenticateUserOutput execute(final AuthenticateUserInput input) {
        var user = userService.getByUsername(input.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(!user.getPassword().equals(input.getPassword())) {
            throw new RuntimeException("User could not be authenticated: " + user.getUsername());
        }

        var generatedToken = jwtUtils.generateToken(user);

        return AuthenticateUserOutput.builder()
                .token(generatedToken)
                .build();
    }
}

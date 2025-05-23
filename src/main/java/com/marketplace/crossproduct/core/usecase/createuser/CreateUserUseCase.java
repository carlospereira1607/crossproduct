package com.marketplace.crossproduct.core.usecase.createuser;

import com.marketplace.crossproduct.core.exception.DuplicatedEntryException;
import com.marketplace.crossproduct.core.model.User;
import com.marketplace.crossproduct.core.service.PortalService;
import com.marketplace.crossproduct.core.service.UserService;
import com.marketplace.crossproduct.core.usecase.UseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateUserUseCase implements UseCase<CreateUserInput, CreateUserOutput> {

    private final UserService userService;
    private final PortalService portalService;

    @Override
    public CreateUserOutput execute(final CreateUserInput input) {
        var portal = portalService.findById(input.getPortalId()).orElseThrow(() -> new RuntimeException("Portal does not exist"));

        var existingUser = userService.getByUsername(input.getUsername());
        if(existingUser.isPresent()) {
            log.error("User {} for portal {} already exists", input.getUsername(), input.getPortalId());
            throw new DuplicatedEntryException("User already exists");
        }

        var user = User.builder()
                .username(input.getUsername())
                .password(input.getPassword())
                .role(input.getRole())
                .portal(portal)
                .build();

        var savedUser = userService.save(user);

        return CreateUserOutput.builder()
                .id(savedUser.getId())
                .username(savedUser.getUsername())
                .password(savedUser.getPassword())
                .role(savedUser.getRole())
                .portalId(savedUser.getPortal().getId())
                .build();
    }

}

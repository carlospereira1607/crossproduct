package com.marketplace.crossproduct.core.usecase.createportal;

import com.marketplace.crossproduct.core.service.PortalService;
import com.marketplace.crossproduct.core.usecase.UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreatePortalUseCase implements UseCase<CreatePortalUseCaseInput, CreatePortalUseCaseOutput> {

    private final PortalService portalService;

    @Override
    public CreatePortalUseCaseOutput execute(CreatePortalUseCaseInput input) {
        var portal = portalService.create(input.getName());
        return CreatePortalUseCaseOutput.builder()
                .name(portal.getName())
                .id(portal.getId())
                .build();
    }
}

package com.marketplace.crossproduct.incoming.mapper;

import com.marketplace.crossproduct.core.usecase.createportal.CreatePortalUseCaseOutput;
import com.marketplace.crossproduct.incoming.dto.createportal.CreatePortalResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreatePortalMapper {

    CreatePortalResponseDto toCreatePortalResponseDto(CreatePortalUseCaseOutput output);

}

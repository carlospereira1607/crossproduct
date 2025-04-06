package com.marketplace.crossproduct.incoming.mapper;

import com.marketplace.crossproduct.core.usecase.createattributevalue.CreateAttributeValueInput;
import com.marketplace.crossproduct.core.usecase.createattributevalue.CreateAttributeValueOutput;
import com.marketplace.crossproduct.incoming.dto.createattributevalue.CreateAttributeValueRequestDto;
import com.marketplace.crossproduct.incoming.dto.createattributevalue.CreateAttributeValueResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreateAttributeValueMapper {

    CreateAttributeValueInput toCreateAttributeValueInput(CreateAttributeValueRequestDto requestDto);

    CreateAttributeValueResponseDto toCreateAttributeValueResponseDto(CreateAttributeValueOutput output);

}

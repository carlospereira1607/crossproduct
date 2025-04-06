package com.marketplace.crossproduct.incoming.mapper;

import com.marketplace.crossproduct.core.usecase.updateattributevalue.UpdateAttributeValueInput;
import com.marketplace.crossproduct.core.usecase.updateattributevalue.UpdateAttributeValueOutput;
import com.marketplace.crossproduct.incoming.dto.updateattributevalue.UpdateAttributeValueRequestDto;
import com.marketplace.crossproduct.incoming.dto.updateattributevalue.UpdateAttributeValueResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UpdateAttributeValueMapper {

    UpdateAttributeValueInput toUpdateAttributeValueInput(UpdateAttributeValueRequestDto requestDto);

    UpdateAttributeValueResponseDto toUpdateAttributeValueResponseDto(UpdateAttributeValueOutput output);

}

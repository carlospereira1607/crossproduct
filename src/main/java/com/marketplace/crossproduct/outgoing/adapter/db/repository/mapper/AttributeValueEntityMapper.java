package com.marketplace.crossproduct.outgoing.adapter.db.repository.mapper;

import com.marketplace.crossproduct.core.model.AttributeValue;
import com.marketplace.crossproduct.outgoing.adapter.db.repository.entity.AttributeValueEntity;
import com.marketplace.crossproduct.outgoing.adapter.db.repository.entity.AttributeValueId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AttributeValueEntityMapper {

    @Mapping(source = "id", target = "id")
    AttributeValueEntity toAttributeValueEntity(AttributeValue entry, AttributeValueId id);

    AttributeValue toAttributeValue(AttributeValueEntity entity);

}

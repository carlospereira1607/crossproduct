package com.marketplace.crossproduct.outgoing.adapter.db.repository.mapper;

import com.marketplace.crossproduct.core.model.AttributeValue;
import com.marketplace.crossproduct.outgoing.adapter.db.repository.entity.AttributeValueEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AttributeValueEntityMapper {

    AttributeValueEntity toAttributeValueEntity(AttributeValue attributeValue);

    AttributeValue toAttributeValue(AttributeValueEntity attributeValueEntity);

}

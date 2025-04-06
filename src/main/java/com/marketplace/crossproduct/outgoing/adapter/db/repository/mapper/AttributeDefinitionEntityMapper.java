package com.marketplace.crossproduct.outgoing.adapter.db.repository.mapper;

import com.marketplace.crossproduct.core.model.AttributeDefinition;
import com.marketplace.crossproduct.outgoing.adapter.db.repository.entity.AttributeDefinitionEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AttributeDefinitionEntityMapper {

    AttributeDefinition toAttributeDefinition(AttributeDefinitionEntity attributeDefinitionEntity);

    AttributeDefinitionEntity toAttributeDefinitionEntity(AttributeDefinition attributeDefinition);

}

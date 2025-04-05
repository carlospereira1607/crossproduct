package com.marketplace.crossproduct.outgoing.adapter.db.repository.mapper;

import com.marketplace.crossproduct.core.model.AttributeDefinition;
import com.marketplace.crossproduct.outgoing.adapter.db.repository.entity.AttributeDefinitionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface AttributeDefinitionEntityMapper {

    AttributeDefinition toAttributeDefinition(AttributeDefinitionEntity attributeDefinitionEntity);

}

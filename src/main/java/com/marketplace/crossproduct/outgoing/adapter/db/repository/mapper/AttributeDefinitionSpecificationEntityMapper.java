package com.marketplace.crossproduct.outgoing.adapter.db.repository.mapper;

import com.marketplace.crossproduct.core.model.AttributeDefinitionSpecification;
import com.marketplace.crossproduct.outgoing.adapter.db.repository.entity.AttributeDefinitionSpecificationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface AttributeDefinitionSpecificationEntityMapper {

    AttributeDefinitionSpecification toAttributeDefinitionSpecification(AttributeDefinitionSpecificationEntity entity);

}

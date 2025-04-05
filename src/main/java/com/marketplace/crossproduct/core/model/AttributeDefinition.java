package com.marketplace.crossproduct.core.model;

import com.marketplace.crossproduct.outgoing.adapter.db.repository.entity.AttributeDefinitionSelectableOption;
import com.marketplace.crossproduct.outgoing.adapter.db.repository.entity.AttributeDefinitionSpecificationEntity;
import com.marketplace.crossproduct.outgoing.adapter.db.repository.entity.AttributeValueEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AttributeDefinition {

    private Long id;

    private String name;

    private AttributeDefinitionType type;

    private AttributeDefinitionSpecificationEntity specification;

    private Set<AttributeDefinitionSelectableOption> selectableOptions;

    private Set<AttributeValueEntity> values;

}

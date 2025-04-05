package com.marketplace.crossproduct.core.model;

import com.marketplace.crossproduct.outgoing.adapter.db.repository.entity.AttributeDefinitionEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AttributeValue {

    private Long id;

    private String value;

    private Boolean isStandard;

    private AttributeDefinitionEntity definition;

}

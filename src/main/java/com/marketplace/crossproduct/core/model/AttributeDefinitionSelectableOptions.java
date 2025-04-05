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
public class AttributeDefinitionSelectableOptions {

    private Long id;

    private String value;

    private AttributeDefinitionEntity definition;

}

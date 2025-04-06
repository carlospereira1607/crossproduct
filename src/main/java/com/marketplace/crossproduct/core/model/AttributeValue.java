package com.marketplace.crossproduct.core.model;

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

    private AttributeDefinition definition;

    private Product product;

}

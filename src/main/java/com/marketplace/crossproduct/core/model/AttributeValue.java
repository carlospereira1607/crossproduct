package com.marketplace.crossproduct.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttributeValue {
    private String value;
    private Boolean isStandard;
    private Portal portal;
    private Product product;
    private AttributeDefinition definition;
}

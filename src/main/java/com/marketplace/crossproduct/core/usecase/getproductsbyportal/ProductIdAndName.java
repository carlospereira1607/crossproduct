package com.marketplace.crossproduct.core.usecase.getproductsbyportal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class ProductIdAndName {

    private Long id;
    private String name;

}

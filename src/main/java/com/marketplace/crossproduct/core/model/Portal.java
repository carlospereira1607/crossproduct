package com.marketplace.crossproduct.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@AllArgsConstructor
@Data
public class Portal {
    private Long id;
    private String name;
}

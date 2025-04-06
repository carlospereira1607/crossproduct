package com.marketplace.crossproduct.incoming.dto.getproductdetails;

import java.util.Set;

public record GetProductDetailsResponseDto(Long id, String name, Set<AttributeValueDetailsDto> attributes) {
}

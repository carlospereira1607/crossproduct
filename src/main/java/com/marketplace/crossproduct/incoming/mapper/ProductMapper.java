package com.marketplace.crossproduct.incoming.mapper;

import com.marketplace.crossproduct.core.model.AttributeDefinition;
import com.marketplace.crossproduct.core.model.AttributeValue;
import com.marketplace.crossproduct.core.model.Product;
import com.marketplace.crossproduct.incoming.dto.getproductdetails.AttributeDefinitionDetailsDto;
import com.marketplace.crossproduct.incoming.dto.getproductdetails.AttributeValueDetailsDto;
import com.marketplace.crossproduct.incoming.dto.getproductdetails.GetProductDetailsResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    GetProductDetailsResponseDto toGetProductDetailsResponseDto(Product product);

    AttributeValueDetailsDto attributeValueToAttributeValueDetailsDto(AttributeValue attributeValue);

    AttributeDefinitionDetailsDto attributeValueToAttributeValueDetailsDto(AttributeDefinition definition);

}

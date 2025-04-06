package com.marketplace.crossproduct.incoming.rest;

import com.marketplace.crossproduct.core.usecase.getproductdetails.GetProductDetailsInput;
import com.marketplace.crossproduct.core.usecase.getproductdetails.GetProductDetailsUseCase;
import com.marketplace.crossproduct.core.usecase.getproductstandardattributes.GetProductStandardAttributesInput;
import com.marketplace.crossproduct.core.usecase.getproductstandardattributes.GetProductStandardAttributesUseCase;
import com.marketplace.crossproduct.incoming.dto.getproductdetails.GetProductDetailsResponseDto;
import com.marketplace.crossproduct.incoming.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final GetProductStandardAttributesUseCase getProductStandardAttributesUseCase;
    private final GetProductDetailsUseCase getProductDetailsUseCase;
    private final ProductMapper productMapper;

    @GetMapping("/product/{productId}")
    public ResponseEntity<GetProductDetailsResponseDto> getProductDetails(@PathVariable Long productId)  {
        var input = GetProductDetailsInput.builder().productId(productId).build();
        var product = getProductDetailsUseCase.execute(input);
        var response = productMapper.toGetProductDetailsResponseDto(product.getProduct());

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/standard/{productId}")
    public ResponseEntity<GetProductDetailsResponseDto> getStandardAttributes(@PathVariable Long productId)  {
        var input = GetProductStandardAttributesInput.builder().productId(productId).build();
        var product = getProductStandardAttributesUseCase.execute(input);
        var response = productMapper.toGetProductDetailsResponseDto(product.getProduct());
        return ResponseEntity.ok().body(response);
    }
}

package com.marketplace.crossproduct.incoming.rest;

import com.marketplace.crossproduct.core.usecase.createproduct.CreateProductUseCase;
import com.marketplace.crossproduct.core.usecase.createproduct.CreateProductUseCaseInput;
import com.marketplace.crossproduct.core.usecase.getproductdetails.GetProductDetailsInput;
import com.marketplace.crossproduct.core.usecase.getproductdetails.GetProductDetailsUseCase;
import com.marketplace.crossproduct.core.usecase.getproductstandardattributes.GetProductStandardAttributesInput;
import com.marketplace.crossproduct.core.usecase.getproductstandardattributes.GetProductStandardAttributesUseCase;
import com.marketplace.crossproduct.incoming.dto.createproduct.CreateProductResponseDto;
import com.marketplace.crossproduct.incoming.dto.createproduct.CreateProductResquestDto;
import com.marketplace.crossproduct.incoming.dto.getproductdetails.GetProductDetailsResponseDto;
import com.marketplace.crossproduct.incoming.mapper.ProductMapper;
import com.marketplace.crossproduct.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final GetProductStandardAttributesUseCase getProductStandardAttributesUseCase;
    private final GetProductDetailsUseCase getProductDetailsUseCase;
    private final CreateProductUseCase createProductUseCase;
    private final ProductMapper productMapper;

    @PostMapping
    public ResponseEntity<CreateProductResponseDto> createProduct(@RequestBody CreateProductResquestDto request) {
        var input = CreateProductUseCaseInput.builder().name(request.name()).build();
        var product = createProductUseCase.execute(input);
        return ResponseEntity.ok().body(productMapper.toCreateProductResponseDto(product));
    }

    @GetMapping("/details/{productId}/{portalId}")
    public ResponseEntity<GetProductDetailsResponseDto> getProductDetails(@PathVariable Long productId,
                                                                          @PathVariable Long portalId)  {
        //TODO validate portal id and role again
        var input = GetProductDetailsInput.builder().productId(productId).portalId(portalId).build();
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

package com.marketplace.crossproduct.incoming.rest;

import com.marketplace.crossproduct.core.usecase.getproductdetails.GetProductDetailsInput;
import com.marketplace.crossproduct.core.usecase.getproductdetails.GetProductDetailsUseCase;
import com.marketplace.crossproduct.core.usecase.getproductsbyportal.GetProductsByPortalInput;
import com.marketplace.crossproduct.core.usecase.getproductsbyportal.GetProductsByPortalUseCase;
import com.marketplace.crossproduct.incoming.dto.getportalproducts.ProductResponseDto;
import com.marketplace.crossproduct.incoming.dto.getproductdetails.GetProductDetailsResponseDto;
import com.marketplace.crossproduct.incoming.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/portal")
@RequiredArgsConstructor
public class PortalController {

    private final GetProductsByPortalUseCase getProductsByPortalUseCase;
    private final GetProductDetailsUseCase getProductDetailsUseCase;
    private final ProductMapper productMapper;

    @GetMapping("/product/{productId}")
    public ResponseEntity<GetProductDetailsResponseDto> getProductDetails(@PathVariable Long productId)  {
        var input = GetProductDetailsInput.builder().productId(productId).build();
        var product = getProductDetailsUseCase.execute(input);
        var response = productMapper.toGetProductDetailsResponseDto(product.getProduct());

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{portalId}/products")
    public ResponseEntity<Set<ProductResponseDto>> getProducts(@PathVariable Long portalId)  {
        var input = GetProductsByPortalInput.builder().portalId(portalId).build();
        var products = getProductsByPortalUseCase.execute(input).getProducts();

        if(products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(
                products.stream()
                        .map(entry -> new ProductResponseDto(entry.getId(), entry.getName()))
                        .collect(Collectors.toSet())
        );
    }

}

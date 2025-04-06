package com.marketplace.crossproduct.incoming.rest;

import com.marketplace.crossproduct.core.usecase.getproductsbyportal.GetProductsByPortalInput;
import com.marketplace.crossproduct.core.usecase.getproductsbyportal.GetProductsByPortalUseCase;
import com.marketplace.crossproduct.incoming.dto.getportalproducts.ProductResponseDto;
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

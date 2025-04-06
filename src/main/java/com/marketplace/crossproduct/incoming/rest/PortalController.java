package com.marketplace.crossproduct.incoming.rest;

import com.marketplace.crossproduct.core.usecase.createportal.CreatePortalUseCase;
import com.marketplace.crossproduct.core.usecase.createportal.CreatePortalUseCaseInput;
import com.marketplace.crossproduct.core.usecase.getproductsbyportal.GetProductsByPortalInput;
import com.marketplace.crossproduct.core.usecase.getproductsbyportal.GetProductsByPortalUseCase;
import com.marketplace.crossproduct.incoming.dto.createportal.CreatePortalRequestDto;
import com.marketplace.crossproduct.incoming.dto.createportal.CreatePortalResponseDto;
import com.marketplace.crossproduct.incoming.dto.getportalproducts.ProductResponseDto;
import com.marketplace.crossproduct.incoming.mapper.CreatePortalMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/portal")
@RequiredArgsConstructor
public class PortalController {

    private final GetProductsByPortalUseCase getProductsByPortalUseCase;
    private final CreatePortalUseCase createPortalUseCase;
    private final CreatePortalMapper createPortalMapper;

    @PostMapping
    public ResponseEntity<CreatePortalResponseDto> createPortal(@RequestBody CreatePortalRequestDto request)  {
        var input = CreatePortalUseCaseInput.builder().name(request.name()).build();
        var result = createPortalUseCase.execute(input);
        return ResponseEntity.ok().body(createPortalMapper.toCreatePortalResponseDto(result));
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

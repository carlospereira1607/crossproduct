package com.marketplace.crossproduct.core.usecase.getproductsbyportal;

import com.marketplace.crossproduct.core.service.ProductService;
import com.marketplace.crossproduct.core.usecase.UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetProductsByPortalUseCase implements UseCase<GetProductsByPortalInput, GetProductsByPortalOutput> {

    private final ProductService productService;

    @Override
    public GetProductsByPortalOutput execute(final GetProductsByPortalInput input) {
        var products = productService.getByPortalId(input.getPortalId());
        var mappedProducts = products.stream().map(entry -> new ProductIdAndName(entry.getId(), entry.getName())).collect(Collectors.toSet());
        return new GetProductsByPortalOutput(mappedProducts);
    }

}

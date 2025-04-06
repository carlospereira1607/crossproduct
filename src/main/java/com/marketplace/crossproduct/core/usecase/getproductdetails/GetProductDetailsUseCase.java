package com.marketplace.crossproduct.core.usecase.getproductdetails;

import com.marketplace.crossproduct.core.service.ProductService;
import com.marketplace.crossproduct.core.usecase.UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetProductDetailsUseCase implements UseCase<GetProductDetailsInput, GetProductDetailsOutput> {

    private final ProductService productService;

    @Override
    public GetProductDetailsOutput execute(final GetProductDetailsInput input) {
        var existingProduct = productService.findById(input.getProductId())
                                                        .orElseThrow(() -> new RuntimeException("Could not find product"));
        return new GetProductDetailsOutput(existingProduct);
    }

}

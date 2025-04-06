package com.marketplace.crossproduct.core.usecase.createproduct;

import com.marketplace.crossproduct.core.service.ProductService;
import com.marketplace.crossproduct.core.usecase.UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateProductUseCase implements UseCase<CreateProductUseCaseInput, CreateProductUseCaseOutput> {

    private final ProductService productService;

    @Override
    public CreateProductUseCaseOutput execute(CreateProductUseCaseInput input) {
        var result = productService.save(input.getName());
        return CreateProductUseCaseOutput.builder()
                .id(result.getId())
                .name(result.getName())
                .build();
    }

}

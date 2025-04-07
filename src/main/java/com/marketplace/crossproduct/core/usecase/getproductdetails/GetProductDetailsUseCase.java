package com.marketplace.crossproduct.core.usecase.getproductdetails;

import com.marketplace.crossproduct.core.exception.DataNotFoundException;
import com.marketplace.crossproduct.core.service.AttributeValueService;
import com.marketplace.crossproduct.core.service.ProductService;
import com.marketplace.crossproduct.core.usecase.UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetProductDetailsUseCase implements UseCase<GetProductDetailsInput, GetProductDetailsOutput> {

    private final ProductService productService;
    private final AttributeValueService attributeValueService;

    @Override
    public GetProductDetailsOutput execute(final GetProductDetailsInput input) {
        var values = attributeValueService.findByProductIdAndPortalId(input.getProductId(), input.getPortalId());

        if(values.isEmpty()) {
            var message = String.format("Could not find any values for product %s and portal %s", input.getProductId(), input.getPortalId());
            throw new DataNotFoundException(message);
        }

        var product = productService.findById(input.getProductId()).orElseThrow(() -> new DataNotFoundException("Could not find product"));
        product.setAttributes(values);

        return new GetProductDetailsOutput(product);
    }

}

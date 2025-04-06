package com.marketplace.crossproduct.core.usecase.getproductstandardattributes;

import com.marketplace.crossproduct.core.model.AttributeValue;
import com.marketplace.crossproduct.core.service.AttributeValueService;
import com.marketplace.crossproduct.core.service.ProductService;
import com.marketplace.crossproduct.core.usecase.UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetProductStandardAttributesUseCase implements UseCase<GetProductStandardAttributesInput, GetProductStandardAttributesOutput> {

    private final ProductService productService;
    private final AttributeValueService attributeValueService;

    @Override
    public GetProductStandardAttributesOutput execute(final GetProductStandardAttributesInput input) {
        var product = productService.findById(input.getProductId()).orElseThrow(() -> new RuntimeException("Product does not exist"));
        var attributeValues = attributeValueService.findByProductId(input.getProductId());

        if(attributeValues.isEmpty()) {
            throw new RuntimeException("There are no attribute values for given product");
        }

        product.setAttributes(filterForStandard(attributeValues));

        return GetProductStandardAttributesOutput.builder().product(product).build();
    }

    Set<AttributeValue> filterForStandard(final Set<AttributeValue> values) {
        return values.stream().filter(AttributeValue::getIsStandard).collect(Collectors.toSet());
    }

}

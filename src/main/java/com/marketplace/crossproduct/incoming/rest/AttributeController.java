package com.marketplace.crossproduct.incoming.rest;

import com.marketplace.crossproduct.core.usecase.createattributedefinition.CreateAttributeDefinitionUseCase;
import com.marketplace.crossproduct.core.usecase.createattributevalue.CreateAttributeValueUseCase;
import com.marketplace.crossproduct.core.usecase.updateattributevalue.UpdateAttributeValueUseCase;
import com.marketplace.crossproduct.incoming.dto.createattributedefinition.CreateAttributeDefinitionRequestDto;
import com.marketplace.crossproduct.incoming.dto.createattributedefinition.CreateAttributeDefinitionResponseDto;
import com.marketplace.crossproduct.incoming.dto.createattributevalue.CreateAttributeValueRequestDto;
import com.marketplace.crossproduct.incoming.dto.createattributevalue.CreateAttributeValueResponseDto;
import com.marketplace.crossproduct.incoming.dto.updateattributevalue.UpdateAttributeValueRequestDto;
import com.marketplace.crossproduct.incoming.dto.updateattributevalue.UpdateAttributeValueResponseDto;
import com.marketplace.crossproduct.incoming.mapper.CreateAttributeDefinitionMapper;
import com.marketplace.crossproduct.incoming.mapper.CreateAttributeValueMapper;
import com.marketplace.crossproduct.incoming.mapper.UpdateAttributeValueMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/attribute")
@RequiredArgsConstructor
public class AttributeController {

    private final CreateAttributeDefinitionUseCase createAttributeDefinitionUseCase;
    private final CreateAttributeValueUseCase createAttributeValueUseCase;
    private final UpdateAttributeValueUseCase updateAttributeValueUseCase;

    private final CreateAttributeDefinitionMapper createAttributeDefinitionMapper;
    private final CreateAttributeValueMapper createAttributeValueMapper;
    private final UpdateAttributeValueMapper updateAttributeValueMapper;

    @PostMapping("/definition")
    public ResponseEntity<CreateAttributeDefinitionResponseDto> createAttributeDefinition(@RequestBody CreateAttributeDefinitionRequestDto request) {
        var input = createAttributeDefinitionMapper.toCreateAttributeDefinitionInput(request);
        var result = createAttributeDefinitionUseCase.execute(input);
        var response = createAttributeDefinitionMapper.toCreateAttributeDefinitionResponseDto(result);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/value")
    public ResponseEntity<CreateAttributeValueResponseDto> createAttributeValue(@RequestBody CreateAttributeValueRequestDto request) {
        var input = createAttributeValueMapper.toCreateAttributeValueInput(request);
        var result = createAttributeValueUseCase.execute(input);
        var response = createAttributeValueMapper.toCreateAttributeValueResponseDto(result);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/value")
    public ResponseEntity updateAttributeValue(@RequestBody UpdateAttributeValueRequestDto request) {
        var input = updateAttributeValueMapper.toUpdateAttributeValueInput(request);
        updateAttributeValueUseCase.execute(input);
        return ResponseEntity.noContent().build();
    }

}

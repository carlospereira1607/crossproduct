package com.marketplace.crossproduct.outgoing.adapter.db.repository.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class AttributeValueId implements Serializable {

    private Long portalId;
    private Long productId;
    private Long definitionId;

}
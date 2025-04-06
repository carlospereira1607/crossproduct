package com.marketplace.crossproduct.outgoing.adapter.db.repository.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class PortalProductAttributeValueId implements Serializable {

    private Long portalId;
    private Long productId;
    private Long attributeValueId;

}
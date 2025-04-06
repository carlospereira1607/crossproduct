package com.marketplace.crossproduct.outgoing.adapter.db.repository.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "portal_product_attribute_definition")
public class PortalProductAttributeDefinitionEntity {

    @EmbeddedId
    private PortalProductAttributeDefinitionId id;

    @ManyToOne
    @MapsId("portalId")
    @JoinColumn(name = "portal_id")
    private PortalEntity portal;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @ManyToOne
    @MapsId("attributeDefinitionId")
    @JoinColumn(name = "attribute_definition_id")
    private AttributeDefinitionEntity definition;

}

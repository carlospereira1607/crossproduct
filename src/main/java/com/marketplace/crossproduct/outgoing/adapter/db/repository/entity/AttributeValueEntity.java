package com.marketplace.crossproduct.outgoing.adapter.db.repository.entity;

import jakarta.persistence.Column;
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
@Table(name = "attribute_value")
public class AttributeValueEntity {

    @EmbeddedId
    private PortalProductAttributeDefinitionId id;

    @Column(nullable = false)
    private String value;

    @Column(name = "is_standard", nullable = false)
    private Boolean isStandard;

    @ManyToOne
    @MapsId("portalId")
    @JoinColumn(name = "portal_id")
    private PortalEntity portal;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @ManyToOne
    @MapsId("definitionId")
    @JoinColumn(name = "definition_id")
    private AttributeDefinitionEntity definition;

}

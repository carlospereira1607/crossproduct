package com.marketplace.crossproduct.outgoing.adapter.db.repository.entity;

import com.marketplace.crossproduct.core.converter.StringSetConverter;
import com.marketplace.crossproduct.core.model.AttributeDefinitionType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "attribute_definition")
public class AttributeDefinitionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AttributeDefinitionType type;

    @ManyToOne
    @JoinColumn(name = "specification_id", nullable = false)
    private AttributeDefinitionSpecificationEntity specification;

    @Convert(converter = StringSetConverter.class)
    @Column(name = "selectable_options", columnDefinition = "TEXT")
    private Set<String> selectableOptions;

    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AttributeValueEntity> values;

    @OneToMany(mappedBy = "attributeValue")
    private Set<PortalProductAttributeDefinitionEntity> portalProductLinks = new HashSet<>();

}

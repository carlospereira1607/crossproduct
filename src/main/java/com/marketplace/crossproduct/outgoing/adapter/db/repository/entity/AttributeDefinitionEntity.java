package com.marketplace.crossproduct.outgoing.adapter.db.repository.entity;

import com.marketplace.crossproduct.core.converter.StringSetConverter;
import com.marketplace.crossproduct.core.model.AttributeDefinitionSpecificationType;
import com.marketplace.crossproduct.core.model.AttributeDefinitionType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
    @Column(name = "definition_type", nullable = false)
    private AttributeDefinitionType definitionType;

    @Column(nullable = false)
    private String value;

    @Enumerated(EnumType.STRING)
    @Column(name = "specification_type", nullable = false)
    private AttributeDefinitionSpecificationType specificationType;

    @Convert(converter = StringSetConverter.class)
    @Column(name = "selectable_options")
    private Set<String> selectableOptions;

    @OneToMany
    @JoinColumn(name = "attribute_definition_id")
    private Set<AttributeValueEntity> attributes = new HashSet<>();

}

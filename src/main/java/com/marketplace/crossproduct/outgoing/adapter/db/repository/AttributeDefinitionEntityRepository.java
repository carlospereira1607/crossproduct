package com.marketplace.crossproduct.outgoing.adapter.db.repository;

import com.marketplace.crossproduct.outgoing.adapter.db.repository.entity.AttributeDefinitionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface AttributeDefinitionEntityRepository extends JpaRepository<AttributeDefinitionEntity, Long> {

    @Query(value = """
        SELECT * FROM attribute_definition
        WHERE name = :name
          AND definition_type = :definitionType
          AND specification_type = :specificationType
          AND specificationValue = :specificationValue
          AND selectable_options = :selectableOptions
    """, nativeQuery = true)
    Optional<AttributeDefinitionEntity> findByNameAndTypeAndSpecificationTypeAndValueAndSelectableOptions(
            @Param("name") String name,
            @Param("definitionType") String definitionType,
            @Param("specificationType") String specificationType,
            @Param("specificationValue") String value,
            @Param("selectableOptions") Set<String> selectableOptions
    );

}

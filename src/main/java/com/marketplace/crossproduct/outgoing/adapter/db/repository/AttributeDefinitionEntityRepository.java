package com.marketplace.crossproduct.outgoing.adapter.db.repository;

import com.marketplace.crossproduct.outgoing.adapter.db.repository.entity.AttributeDefinitionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AttributeDefinitionEntityRepository extends JpaRepository<AttributeDefinitionEntity, Long> {

    @Query(value = """
        SELECT * FROM attribute_definition
        WHERE name = :name
          AND definition_type = :definitionType
          AND specification_type = :specificationType
    """, nativeQuery = true)
    Optional<AttributeDefinitionEntity> findByNameAndTypeAndSpecificationType(
            @Param("name") String name,
            @Param("definitionType") String definitionType,
            @Param("specificationType") String specificationType
    );

}

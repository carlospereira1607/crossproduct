package com.marketplace.crossproduct.outgoing.adapter.db.repository;

import com.marketplace.crossproduct.core.model.AttributeDefinitionType;
import com.marketplace.crossproduct.outgoing.adapter.db.repository.entity.AttributeDefinitionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface AttributeDefinitionEntityRepository extends JpaRepository<AttributeDefinitionEntity, Long> {

    @Query("""
        SELECT a FROM AttributeDefinitionEntity a
        WHERE a.name = :name
          AND a.type = :type
          AND a.specification.id = :specificationId
          AND a.selectableOptions = :selectableOptions
    """)
    Optional<AttributeDefinitionEntity> findByNameAndTypeAndSpecificationIdAndSelectableOptions(
            @Param("name") String name,
            @Param("type") AttributeDefinitionType type,
            @Param("specificationId") Long specificationId,
            @Param("selectableOptions") Set<String> selectableOptions
    );

}

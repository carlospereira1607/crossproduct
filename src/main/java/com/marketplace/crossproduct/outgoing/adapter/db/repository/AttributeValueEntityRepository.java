package com.marketplace.crossproduct.outgoing.adapter.db.repository;

import com.marketplace.crossproduct.outgoing.adapter.db.repository.entity.AttributeValueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AttributeValueEntityRepository extends JpaRepository<AttributeValueEntity, Long> {

    @Query(value = "SELECT * FROM attribute_value WHERE product_id = :productId AND portal_id = :portalId AND definition_id = :definitionId", nativeQuery = true)
    Optional<AttributeValueEntity> findByProductIdAndPortalIdAndDefinitionId(@Param("productId") Long productId, @Param("portalId") Long portalId, @Param("definitionId") Long definitionId);

    List<AttributeValueEntity> findByProductId(Long productId);

    @Query("SELECT av FROM AttributeValueEntity av WHERE av.product.id = :productId AND av.portal.id = :portalId")
    List<AttributeValueEntity> findByProductIdAndPortalId(@Param("productId") Long productId, @Param("portalId") Long portalId);

}

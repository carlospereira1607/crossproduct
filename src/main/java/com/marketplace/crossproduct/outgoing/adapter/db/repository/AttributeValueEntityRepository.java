package com.marketplace.crossproduct.outgoing.adapter.db.repository;

import com.marketplace.crossproduct.outgoing.adapter.db.repository.entity.AttributeValueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AttributeValueEntityRepository extends JpaRepository<AttributeValueEntity, Long> {

    Optional<AttributeValueEntity> findByProductIdAndPortalIdAndDefinitionId(Long productId, Long portalId, Long definitionId);

    @Query("SELECT av FROM AttributeValueEntity av WHERE av.product.id = :productId AND av.portal.id = :portalId")
    List<AttributeValueEntity> findByProductIdAndPortalId(@Param("productId") Long productId, @Param("portalId") Long portalId);

}

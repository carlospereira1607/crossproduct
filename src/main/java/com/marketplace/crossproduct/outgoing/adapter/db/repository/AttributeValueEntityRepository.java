package com.marketplace.crossproduct.outgoing.adapter.db.repository;

import com.marketplace.crossproduct.outgoing.adapter.db.repository.entity.AttributeValueEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AttributeValueEntityRepository extends JpaRepository<AttributeValueEntity, Long> {

    Optional<AttributeValueEntity> findByProductIdAndPortalIdAndDefinitionId(Long productId, Long portalId, Long definitionId);

}

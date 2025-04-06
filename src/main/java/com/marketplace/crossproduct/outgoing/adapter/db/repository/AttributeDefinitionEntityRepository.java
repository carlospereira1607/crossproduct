package com.marketplace.crossproduct.outgoing.adapter.db.repository;

import com.marketplace.crossproduct.outgoing.adapter.db.repository.entity.AttributeDefinitionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AttributeDefinitionEntityRepository extends JpaRepository<AttributeDefinitionEntity, Long> {

//    Optional<AttributeDefinitionEntity> findByNameAndPortalId(String name, Long portalId);

}

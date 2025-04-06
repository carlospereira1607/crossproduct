package com.marketplace.crossproduct.outgoing.adapter.db.repository;

import com.marketplace.crossproduct.outgoing.adapter.db.repository.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface ProductEntityRepository extends JpaRepository<ProductEntity, Long>  {

    @Query("SELECT p FROM Product p " +
            "JOIN p.attributes a " +
            "JOIN a.definition d " +
            "WHERE d.portal.id = :portalId")
    Set<ProductEntity> findAllByPortalId(@Param("portalId") Long portalId);

}

package com.marketplace.crossproduct.outgoing.adapter.db.repository;

import com.marketplace.crossproduct.outgoing.adapter.db.repository.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface ProductEntityRepository extends JpaRepository<ProductEntity, Long> {

    @Query(value = """ 
                SELECT p.* FROM product p INNER JOIN attribute_value av ON av.product_id = p.id
                        INNER JOIN attribute_definition ad ON ad.id = av.definition_id
                WHERE ad.portal_id = :portalId""", nativeQuery = true)
    Set<ProductEntity> findAllByPortalId(@Param("portalId") Long portalId);

}

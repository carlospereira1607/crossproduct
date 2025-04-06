package com.marketplace.crossproduct.outgoing.adapter.repository;

import com.marketplace.crossproduct.outgoing.adapter.db.repository.PortalEntityRepository;
import com.marketplace.crossproduct.outgoing.adapter.db.repository.entity.PortalEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
class PortalEntityRepositoryTest {

    @Autowired
    PortalEntityRepository portalEntityRepository;

    private PortalEntity portalEntity;

    @BeforeEach
    void setUp() {
        portalEntity = PortalEntity.builder().name("name").users(List.of()).attributes(Set.of()).build();
        portalEntityRepository.save(portalEntity);
    }

    @AfterEach
    void tearDown() {
        portalEntityRepository.delete(portalEntity);
    }

    @Test
    void testFindById_ShouldReturnPortal_WhenPortalExists() {
        Optional<PortalEntity> result = portalEntityRepository.findById(portalEntity.getId());

        assertTrue(result.isPresent(), "Portal should be present.");
        assertEquals("name", result.get().getName(), "Portal name should match.");
    }

}

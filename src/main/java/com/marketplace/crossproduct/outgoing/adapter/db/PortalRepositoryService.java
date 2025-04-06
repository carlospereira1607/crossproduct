package com.marketplace.crossproduct.outgoing.adapter.db;

import com.marketplace.crossproduct.core.model.Portal;
import com.marketplace.crossproduct.core.port.db.PortalPortRepository;
import com.marketplace.crossproduct.outgoing.adapter.db.repository.PortalEntityRepository;
import com.marketplace.crossproduct.outgoing.adapter.db.repository.entity.PortalEntity;
import com.marketplace.crossproduct.outgoing.adapter.db.repository.mapper.PortalEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PortalRepositoryService implements PortalPortRepository {

    private final PortalEntityRepository repository;
    private final PortalEntityMapper mapper;

    @Override
    public Optional<Portal> findById(Long id) {
        var existingPortal = repository.findById(id);
        return existingPortal.map(mapper::toPortal);
    }

    @Override
    public Portal save(String name) {
        var portal = PortalEntity.builder().name(name).build();
        return mapper.toPortal(repository.save(portal));
    }

}

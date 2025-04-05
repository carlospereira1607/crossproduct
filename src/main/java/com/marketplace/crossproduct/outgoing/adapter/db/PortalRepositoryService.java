package com.marketplace.crossproduct.outgoing.adapter.db;

import com.marketplace.crossproduct.core.model.Portal;
import com.marketplace.crossproduct.core.port.db.PortalPortRepository;
import com.marketplace.crossproduct.outgoing.adapter.db.repository.PortalEntityRepository;
import com.marketplace.crossproduct.outgoing.adapter.db.repository.mapper.PortalEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PortalRepositoryService implements PortalPortRepository {

    private final PortalEntityRepository portalEntityRepository;
    private final PortalEntityMapper portalEntityMapper;

    @Override
    public Optional<Portal> findById(Long id) {
        var existingPortal = portalEntityRepository.findById(id);
        return existingPortal.map(portalEntityMapper::toPortal);
    }

}

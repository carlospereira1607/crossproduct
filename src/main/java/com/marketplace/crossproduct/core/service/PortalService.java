package com.marketplace.crossproduct.core.service;

import com.marketplace.crossproduct.core.model.Portal;
import com.marketplace.crossproduct.outgoing.adapter.db.repository.PortalEntityRepository;
import com.marketplace.crossproduct.outgoing.adapter.db.repository.entity.PortalEntity;
import com.marketplace.crossproduct.outgoing.adapter.db.repository.mapper.PortalEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PortalService {

    private final PortalEntityRepository portalEntityRepository;
    private final PortalEntityMapper portalEntityMapper;

    public Portal createPortal(final String name) {
        var portal = PortalEntity.builder()
                .name(name)
                .build();
        return portalEntityMapper.toPortal(portalEntityRepository.save(portal));
    }

}

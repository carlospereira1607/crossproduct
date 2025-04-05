package com.marketplace.crossproduct.core.service;

import com.marketplace.crossproduct.core.model.Portal;
import com.marketplace.crossproduct.core.port.db.PortalPortRepository;
import com.marketplace.crossproduct.outgoing.adapter.db.repository.PortalEntityRepository;
import com.marketplace.crossproduct.outgoing.adapter.db.repository.entity.PortalEntity;
import com.marketplace.crossproduct.outgoing.adapter.db.repository.mapper.PortalEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PortalService {

    private final PortalPortRepository portalPortRepository;

    public Optional<Portal> getById(final Long id) {
        return portalPortRepository.findById(id);
    }

}

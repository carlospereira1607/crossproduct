package com.marketplace.crossproduct.core.service;

import com.marketplace.crossproduct.core.model.Portal;
import com.marketplace.crossproduct.core.port.db.PortalPortRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PortalService {

    private final PortalPortRepository portalPortRepository;

    public Portal create(final String name) {
        return portalPortRepository.save(name);
    }

    public Optional<Portal> findById(final Long id) {
        return portalPortRepository.findById(id);
    }

}

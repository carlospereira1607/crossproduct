package com.marketplace.crossproduct.outgoing.adapter.db.repository.mapper;

import com.marketplace.crossproduct.core.model.Portal;
import com.marketplace.crossproduct.outgoing.adapter.db.repository.entity.PortalEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PortalEntityMapper {

    Portal toPortal(PortalEntity portalEntity);

    PortalEntity toPortalEntity(Portal portal);

}

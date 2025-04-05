package com.marketplace.crossproduct.outgoing.db.mapper;

import com.marketplace.crossproduct.core.model.Portal;
import com.marketplace.crossproduct.outgoing.db.entity.PortalEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface PortalEntityMapper {

    Portal toPortal(PortalEntity portalEntity);

}

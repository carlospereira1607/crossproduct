package com.marketplace.crossproduct.outgoing.db.mapper;

import com.marketplace.crossproduct.core.model.User;
import com.marketplace.crossproduct.outgoing.db.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface UserEntityMapper {

    UserEntity toUserEntity(User user);

    User toUser(UserEntity userEntity);

}

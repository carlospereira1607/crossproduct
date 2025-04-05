package com.marketplace.crossproduct.outgoing.adapter.db.repository.mapper;

import com.marketplace.crossproduct.core.model.User;
import com.marketplace.crossproduct.outgoing.adapter.db.repository.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserEntityMapper {

    UserEntity toUserEntity(User user);

    User toUser(UserEntity userEntity);

}

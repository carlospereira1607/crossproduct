package com.marketplace.crossproduct.core.service;

import com.marketplace.crossproduct.core.model.User;
import com.marketplace.crossproduct.outgoing.db.UserEntityRepository;
import com.marketplace.crossproduct.outgoing.db.entity.UserEntity;
import com.marketplace.crossproduct.outgoing.db.mapper.UserEntityMapper;
import com.marketplace.crossproduct.security.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserEntityRepository userEntityRepository;
    private final UserEntityMapper userEntityMapper;

    public User createUser(final String username, final String password, final Role role) {
        var user = UserEntity.builder()
                .username(username)
                .password(password)
                .role(role)
                .build();
        return userEntityMapper.toUser(userEntityRepository.save(user));
    }
}
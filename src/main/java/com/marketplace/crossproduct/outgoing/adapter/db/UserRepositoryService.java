package com.marketplace.crossproduct.outgoing.adapter.db;

import com.marketplace.crossproduct.core.model.User;
import com.marketplace.crossproduct.core.port.db.UserPortRepository;
import com.marketplace.crossproduct.outgoing.adapter.db.repository.UserEntityRepository;
import com.marketplace.crossproduct.outgoing.adapter.db.repository.mapper.UserEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryService implements UserPortRepository {

    private final UserEntityRepository userEntityRepository;
    private final UserEntityMapper userEntityMapper;

    @Override
    public User save(User user) {
        var userEntity = userEntityMapper.toUserEntity(user);
        return userEntityMapper.toUser(userEntityRepository.save(userEntity));
    }

    @Override
    public Optional<User> findByUsername(final String username) {
        var existingUser = userEntityRepository.findByUsername(username);
        return existingUser.map(userEntityMapper::toUser);
    }

}

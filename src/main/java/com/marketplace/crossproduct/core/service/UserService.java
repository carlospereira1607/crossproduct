package com.marketplace.crossproduct.core.service;

import com.marketplace.crossproduct.core.model.User;
import com.marketplace.crossproduct.core.port.db.UserPortRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserPortRepository userPortRepository;

    public Optional<User> getByUsername(final String username) {
        return userPortRepository.findByUsername(username);
    }

    public User save(final User user) {
        return userPortRepository.save(user);
    }

}
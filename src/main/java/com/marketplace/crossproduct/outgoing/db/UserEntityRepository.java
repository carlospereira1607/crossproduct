package com.marketplace.crossproduct.outgoing.db;

import com.marketplace.crossproduct.outgoing.db.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);

}

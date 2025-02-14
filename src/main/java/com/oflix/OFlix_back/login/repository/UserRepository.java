package com.oflix.OFlix_back.login.repository;

import com.oflix.OFlix_back.login.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    Boolean existsByUsername(String username);

    Optional<UserEntity> findByUsername(String username);


}

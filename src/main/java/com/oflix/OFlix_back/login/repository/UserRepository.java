package com.oflix.OFlix_back.login.repository;

import com.oflix.OFlix_back.login.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);


}

package com.world.task.repository;

import com.world.task.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, String> {

    Optional<UserModel> findByEmail(String email);

    boolean existsByEmail(String email);
}

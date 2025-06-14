package com.docdock.group09.user_service.repository;

import com.docdock.group09.user_service.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
    Optional<UserEntity> findByName(String name);
    List<UserEntity> findByNameContainingIgnoreCase(String name);
    Optional<UserEntity> findByEmail(String email);
}

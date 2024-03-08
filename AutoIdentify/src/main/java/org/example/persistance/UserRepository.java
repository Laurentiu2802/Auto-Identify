package org.example.persistance;

import org.example.persistance.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Optional<UserEntity> getUserByID(Long userID);
    UserEntity save(UserEntity user);
    void deleteByID(Long userID);
    List<UserEntity> getAllUsers();
    Optional<UserEntity> findByID(long userID);
}

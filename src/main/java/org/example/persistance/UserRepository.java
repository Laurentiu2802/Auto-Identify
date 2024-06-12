package org.example.persistance;

import org.example.persistance.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository  extends JpaRepository<UserEntity, Long> {
    @Query("SELECT u FROM UserEntity u WHERE u.userID = ?1")
    UserEntity getUserByID(Long userID);
    @Query("SELECT u FROM UserEntity u")
    List<UserEntity> getAllUsers();
    void deleteByUserID(long userID);
    Optional<UserEntity> findByUserID(Long userID);
    Optional<UserEntity> findByUsername(String username);
}

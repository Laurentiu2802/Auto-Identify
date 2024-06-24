package org.example.persistance;

import org.example.persistance.entity.LikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<LikeEntity, Long> {
    boolean existsByUserUserIDAndPostPostID(Long userID, Long postID);

    Optional<LikeEntity> findByUserUserIDAndPostPostID(Long userID, Long postID);

    void deleteByUser_UserID(Long userID);
}

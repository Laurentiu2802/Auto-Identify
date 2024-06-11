package org.example.persistance;

import org.example.persistance.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    @Query("SELECT c FROM CommentEntity c WHERE c.post.postID = :postID")
    List<CommentEntity> findAllByPostId(@Param("postID") Long postID);
}

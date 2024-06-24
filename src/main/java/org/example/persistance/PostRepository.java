package org.example.persistance;

import org.example.domain.CountPosts;
import org.example.persistance.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<PostEntity, Long> {
    @Query("SELECT p FROM PostEntity p WHERE " +
            "(:categoryID IS NULL OR p.category.categoryID = :categoryID) AND " +
            "(:brandID IS NULL OR p.carBrand.carBrandID = :brandID) AND " +
            "(:modelID IS NULL OR p.carModel.modelID = :modelID)" +
            "ORDER BY p.postID DESC")
    List<PostEntity> findPostsByCriteria(@Param("categoryID") Long categoryID,
                                         @Param("brandID") Long brandID,
                                         @Param("modelID") Long modelID);

    @Query("SELECT NEW org.example.domain.CountPosts(p.user.userID, u.username, COUNT(p))" +
            "FROM PostEntity p INNER JOIN p.user as u GROUP BY p.user.userID, u.username")
    List<CountPosts> getUserPostCounts();

    @Query("SELECT COUNT(l) FROM LikeEntity l WHERE l.post.postID = :postID")
    Long countLikesForPost(Long postID);

    @Query("SELECT p FROM PostEntity p WHERE p.postID = :postID")
    Optional<PostEntity> findPostById(@Param("postID") Long postID);

    void deleteByUser_UserID(Long userID);
}

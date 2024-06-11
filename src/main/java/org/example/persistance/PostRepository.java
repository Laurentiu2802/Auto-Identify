package org.example.persistance;

import org.example.persistance.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<PostEntity, Long> {
    @Query("SELECT p FROM PostEntity p WHERE " +
            "(:categoryID IS NULL OR p.category.categoryID = :categoryID) AND " +
            "(:brandID IS NULL OR p.carBrand.brandID = :brandID) AND " +
            "(:modelID IS NULL OR p.carModel.modelID = :modelID)")
    List<PostEntity> findPostsByCriteria(@Param("categoryID") Long categoryID,
                                         @Param("brandID") Long brandID,
                                         @Param("modelID") Long modelID);
}

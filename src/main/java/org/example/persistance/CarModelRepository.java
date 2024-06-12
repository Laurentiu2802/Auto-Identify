package org.example.persistance;

import org.example.persistance.entity.CarModelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CarModelRepository extends JpaRepository<CarModelEntity, Long> {
    @Query("SELECT c FROM CarModelEntity c WHERE c.carBrand.carBrandID = :brandID")
    List<CarModelEntity> findAllByCarBrandId(@Param("brandID") Long brandID);
}

package org.example.persistance;

import org.example.persistance.entity.CarBrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarBrandRepository extends JpaRepository<CarBrandEntity, Long> {
    Optional<CarBrandEntity> findByCarBrandID(Long carBrandID);

}

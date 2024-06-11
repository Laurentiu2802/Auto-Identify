package org.example.persistance;

import org.example.persistance.entity.CarBrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarBrandRepository extends JpaRepository<CarBrandEntity, Long> {

}

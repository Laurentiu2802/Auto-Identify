package org.example.persistance;

import org.example.persistance.entity.RoleEnum;
import org.example.persistance.entity.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<UserRoleEntity, Long> {
    UserRoleEntity findByRole(RoleEnum roleEnum);
}

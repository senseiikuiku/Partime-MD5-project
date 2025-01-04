package com.ra.repository;

import com.ra.model.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepository extends JpaRepository<Roles, Long> {
    Roles findRoleByRoleName(String roleName);
}

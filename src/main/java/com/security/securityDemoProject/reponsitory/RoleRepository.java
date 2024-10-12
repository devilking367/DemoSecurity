package com.security.securityDemoProject.reponsitory;

import com.security.securityDemoProject.entity.RoleEntity;
import com.security.securityDemoProject.entity.RoleName;
import com.security.securityDemoProject.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByName(RoleName  roleName);
    Optional<RoleEntity> existsByName(RoleName roleName);
}
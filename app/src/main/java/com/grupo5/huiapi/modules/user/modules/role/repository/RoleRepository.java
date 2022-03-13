package com.grupo5.huiapi.modules.user.modules.role.repository;

import com.grupo5.huiapi.modules.user.modules.role.entity.Role;

import java.util.Optional;

public interface RoleRepository extends org.springframework.data.jpa.repository.JpaRepository<Role, Long> {
    public Optional<Role> findByName(String role);

}
package com.grupo5.huiapi.modules.user.modules.role.repository;

import com.grupo5.huiapi.modules.user.modules.role.RoleType;
import com.grupo5.huiapi.modules.user.modules.role.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    public Role findByRole(RoleType role);
}

package com.grupo5.huiapi.modules.user.modules.role.service;

import com.grupo5.huiapi.exceptions.EntityNotFoundException;
import com.grupo5.huiapi.modules.EntityType;
import com.grupo5.huiapi.modules.user.entity.User;
import com.grupo5.huiapi.modules.user.modules.role.entity.Role;
import com.grupo5.huiapi.modules.user.modules.role.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service @Transactional
@Qualifier("RolesService")
public class RoleService implements Roles {
    public final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public String addRoleToUser(String roleName, User user) throws EntityNotFoundException {
        Optional<Role> optionalRole = roleRepository.findByName(roleName);
        if(optionalRole.isEmpty())
            throw new EntityNotFoundException(EntityType.ROLE);

        return null;
    }
}

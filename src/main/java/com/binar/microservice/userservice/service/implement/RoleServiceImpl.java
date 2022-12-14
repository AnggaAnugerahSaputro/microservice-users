package com.binar.microservice.userservice.service.implement;

import com.binar.microservice.userservice.model.entity.Roles;
import com.binar.microservice.userservice.model.request.RoleRequest;
import com.binar.microservice.userservice.model.response.RoleResponse;
import com.binar.microservice.userservice.repository.RoleRepository;
import com.binar.microservice.userservice.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public RoleResponse registerRole(RoleRequest roleRequest) {
        Roles roles = roleRequest.toRoles();
        try {
            roleRepository.save(roles);
            return RoleResponse.build(roles);
        }
        catch(Exception exception)
        {
            return null;
        }
    }

    @Override
    public List<RoleResponse> searchAllRole() {
        List<Roles> allRole = roleRepository.findAll();
        List<RoleResponse> allRoleResponse = new ArrayList<>();
        for (Roles roles: allRole) {
            RoleResponse roleResponse = RoleResponse.build(roles);
            allRoleResponse.add(roleResponse);
        }
        return allRoleResponse;
    }

    @Override
    public RoleResponse updateRole(RoleRequest roleRequest, String roleName) {
        Roles roles = roleRepository.findByName(roleName);
        if(roles != null){
            roles.setRoleName(roleRequest.getRoleName());
            try {
                roleRepository.save(roles);
                return RoleResponse.build(roles);
            }
            catch(Exception exception)
            {
                return null;
            }
        }
        else
            throw new RuntimeException("Roles with name : " + roleName + " not found");
    }

    @Override
    public Boolean deleteRole(String roleName) {
        Roles roles = roleRepository.findByName(roleName);
        if(roles != null) {
            roleRepository.deleteById(roles.getRoleId());
            return true;
        }
        else
            return false;
    }
}

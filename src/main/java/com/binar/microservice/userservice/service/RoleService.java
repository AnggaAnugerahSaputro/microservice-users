package com.binar.microservice.userservice.service;

import com.binar.microservice.userservice.model.request.RoleRequest;
import com.binar.microservice.userservice.model.response.RoleResponse;

import java.util.List;

public interface RoleService {
    RoleResponse registerRole(RoleRequest roleRequest);
    List<RoleResponse> searchAllRole();
    RoleResponse updateRole(RoleRequest roleRequest, String roleName);
    Boolean deleteRole(String roleName);
}

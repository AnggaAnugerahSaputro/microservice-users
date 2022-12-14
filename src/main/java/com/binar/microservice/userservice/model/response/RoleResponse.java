package com.binar.microservice.userservice.model.response;

import com.binar.microservice.userservice.model.entity.Roles;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoleResponse {
    private Integer roleId;
    private String roleName;

    public static RoleResponse build(Roles roles) {
        return RoleResponse.builder()
                .roleId(roles.getRoleId())
                .roleName(roles.getRoleName())
                .build();
    }
}

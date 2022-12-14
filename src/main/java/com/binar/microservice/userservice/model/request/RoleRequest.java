package com.binar.microservice.userservice.model.request;

import com.binar.microservice.userservice.model.entity.Roles;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class RoleRequest {

    @NotEmpty(message = "Role name is required.")
    private String roleName;

    public Roles toRoles() {
        return Roles.builder()
                .roleName(this.roleName)
                .build();
    }
}

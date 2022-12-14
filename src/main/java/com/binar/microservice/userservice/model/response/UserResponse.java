package com.binar.microservice.userservice.model.response;

import com.binar.microservice.userservice.model.entity.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {
    private UUID userId;
    private String fullName;
    private String email;
    private String telephone;
    private LocalDate birthDate;
    private Boolean gender;
    private Integer rolesId;

    public static UserResponse build(User users) {
        return UserResponse.builder()
                .userId(users.getUserId())
                .fullName(users.getFullName())
                .email(users.getEmail())
                .telephone(users.getTelephone())
                .birthDate(users.getBirthDate())
                .gender(users.getGender())
                .rolesId(users.getRolesUsers().getRoleId())
                .build();
    }
}

package com.binar.microservice.userservice.model.request;

import com.binar.microservice.userservice.model.entity.Roles;
import com.binar.microservice.userservice.model.entity.User;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserRequest {
    @NotEmpty(message = "fullName is required.")
    private String fullName;
    @NotEmpty(message = "email is required.")
    private String email;
    @NotEmpty(message = "password is required.")
    private String password;
    @NotEmpty(message = "telephone is required.")
    private String telephone;
    @NotEmpty(message = "birthDate is required.")
    private LocalDate birthDate;
    @NotEmpty(message = "gender is required.")
    private Boolean gender;

    @NotEmpty(message = "rolesUsers is required.")
    private Integer rolesId;

    public User toUsers(Roles roles) {
        User users = new User();
        users.setFullName(this.fullName);
        users.setEmail(this.email);
        users.setPassword(this.password);
        users.setTelephone(this.telephone);
        users.setBirthDate(this.birthDate);
        users.setGender(this.gender);
        users.setRolesUsers(roles);
        return users;
    }
}

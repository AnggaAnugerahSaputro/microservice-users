package com.binar.microservice.userservice.service;

import com.binar.microservice.userservice.model.request.UserRequest;
import com.binar.microservice.userservice.model.response.UserResponse;

import java.util.List;
import java.util.UUID;

public interface UserService {

    UserResponse registerUser(UserRequest userRequest);
    List<UserResponse> searchAllUser();
    UserResponse searchUserByName(String fullName);
    Boolean isEmailExist(String email);
    Boolean isPhoneNumberExist(String telephone);
    Boolean deleteUser(UUID userId);
}

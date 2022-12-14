package com.binar.microservice.userservice.service.implement;

import com.binar.microservice.userservice.configuration.EncoderConfiguration;
import com.binar.microservice.userservice.model.entity.Roles;
import com.binar.microservice.userservice.model.entity.User;
import com.binar.microservice.userservice.model.request.UserRequest;
import com.binar.microservice.userservice.model.response.UserResponse;
import com.binar.microservice.userservice.repository.RoleRepository;
import com.binar.microservice.userservice.repository.UserRepository;
import com.binar.microservice.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    private final RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserResponse registerUser(UserRequest userRequest) {
        if(!isPhoneNumberExist(userRequest.getTelephone()))
        {
            if(!isEmailExist(userRequest.getEmail()))
            {
                try {
                    Optional<Roles> roles = roleRepository.findById(userRequest.getRolesId());
                    if(roles.isPresent())
                    {
                        User users = User.builder()
                                .fullName(userRequest.getFullName())
                                .email(userRequest.getEmail())
                                .telephone(userRequest.getTelephone())
                                .password(userRequest.getPassword())
                                .birthDate(userRequest.getBirthDate())
                                .gender(userRequest.getGender())
                                .telephone(userRequest.getTelephone())
                                .rolesUsers(roles.get())
                                .statusActive(true)
                                .build();

                        users.setPassword(EncoderConfiguration.encrypt(users.getPassword()));
                        userRepository.saveAndFlush(users);
                        return UserResponse.build(users);
                    }
                    else
                        return null;
                }
                catch (Exception ignore){
                    return null;
                }
            }
            else {
                return null;
            }
        }
        else {
            return null;
        }
    }

    @Override
    public List<UserResponse> searchAllUser() {
        List<User> allUser = userRepository.findAll();
        return toListUserResponses(allUser);
    }

    @Override
    public UserResponse searchUserByName(String fullName) {
        User users = userRepository.findByName(fullName);
        if(users != null)
            return UserResponse.build(users);
        else {
            return null;
        }
    }

    @Override
    public Boolean isEmailExist(String email) {
        User users = userRepository.findByEmail(email);
        return users != null;
    }

    @Override
    public Boolean isPhoneNumberExist(String telephone) {
        User users = userRepository.findPhoneNumber(telephone);
        return users != null;
    }

    @Override
    public Boolean deleteUser(UUID userId) {
        Optional<User> users = userRepository.findById(userId);
        if(users.isPresent())
        {
            userRepository.deleteById(userId);
            return true;
        }
        else {
            return false;
        }
    }

    private List<UserResponse> toListUserResponses(List<User> allUser) {
        List<UserResponse> allUserResponse = new ArrayList<>();
        for (User user : allUser) {
            UserResponse userResponse = UserResponse.build(user);
            allUserResponse.add(userResponse);
        }
        return allUserResponse;
    }
}

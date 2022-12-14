package com.binar.microservice.userservice.controller;

import com.binar.microservice.userservice.model.request.UserRequest;
import com.binar.microservice.userservice.model.response.ResponseMessage;
import com.binar.microservice.userservice.model.response.UserResponse;
import com.binar.microservice.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ResponseMessage> registerUser(@RequestBody UserRequest userRequest) {
        ResponseMessage message = new ResponseMessage();

        UserResponse userResponse = userService.registerUser(userRequest);
        if (userResponse == null) {
            message.setMessage("Failed register new user");
            message.setStatus(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(message);
        } else {
            message.setStatus(HttpStatus.OK.value());
            message.setMessage("Register new user");
            message.setData(userResponse);
            return ResponseEntity.ok().body(message);
        }
    }

    @GetMapping("/get-all")
    public ResponseEntity<ResponseMessage> getAllUsers() {
        ResponseMessage message = new ResponseMessage();
        try {
            List<UserResponse> usersGet = userService.searchAllUser();
            message.setMessage("Success get all user");
            message.setStatus(HttpStatus.OK.value());
            message.setData(usersGet);
            return ResponseEntity.ok().body(message);
        } catch (Exception exception) {
            message.setMessage("Failed get all user");
            message.setStatus(HttpStatus.BAD_GATEWAY.value());
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY.value()).body(message);
        }
    }

    @GetMapping("/name/{fullName}")
    public ResponseEntity<ResponseMessage> getUserByFullName(@PathVariable String fullName) {
        ResponseMessage message = new ResponseMessage();
        try {
            UserResponse userGet = userService.searchUserByName(fullName);
            message.setMessage("Success get user");
            message.setStatus(HttpStatus.OK.value());
            message.setData(userGet);
            return ResponseEntity.ok().body(message);
        } catch (Exception exception) {
            message.setMessage("Failed get user");
            message.setStatus(HttpStatus.BAD_GATEWAY.value());
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY.value()).body(message);
        }
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<ResponseMessage> deleteUser(@PathVariable UUID userId) {
        ResponseMessage message = new ResponseMessage();
        Boolean deleteUser = userService.deleteUser(userId);
        if (deleteUser) {
            message.setMessage("Success delete user by id");
            message.setStatus(HttpStatus.OK.value());
            return ResponseEntity.ok().body(message);
        } else {
            message.setMessage("Failed delete user by id not found");
            message.setStatus(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(message);
        }
    }
}

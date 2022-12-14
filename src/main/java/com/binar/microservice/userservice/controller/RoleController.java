package com.binar.microservice.userservice.controller;

import com.binar.microservice.userservice.model.request.RoleRequest;
import com.binar.microservice.userservice.model.request.UserRequest;
import com.binar.microservice.userservice.model.response.ResponseMessage;
import com.binar.microservice.userservice.model.response.RoleResponse;
import com.binar.microservice.userservice.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roles/")
public class RoleController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ResponseMessage> createRole(@RequestBody RoleRequest roleRequest)
    {
        ResponseMessage message = new ResponseMessage();
        RoleResponse roleResponse = roleService.registerRole(roleRequest);
        if(roleResponse == null)
        {
            message.setStatus(HttpStatus.BAD_REQUEST.value());
            message.setMessage("Failed to create roles");
            return ResponseEntity.badRequest().body(message);
        }
        else
        {
            message.setStatus(HttpStatus.CREATED.value());
            message.setMessage("Create new roles");
            message.setData(roleResponse);
            return ResponseEntity.ok().body(message);

        }
    }

    @GetMapping(value = "/get-all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseMessage> getAllRole()
    {
        ResponseMessage message = new ResponseMessage();
        try {
            List<RoleResponse> rolesGet = roleService.searchAllRole();
            message.setMessage("Success get all role");
            message.setStatus(HttpStatus.OK.value());
            message.setData(rolesGet);
            return ResponseEntity.ok().body(message);
        }catch (Exception exception)
        {
            message.setMessage("Failed get all role");
            message.setStatus(HttpStatus.BAD_GATEWAY.value());
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY.value()).body(message);
        }
    }

    @PutMapping(value = "/update/{roleName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseMessage> updateRole(@PathVariable String roleName, @RequestBody RoleRequest roleRequest)
    {
        ResponseMessage message = new ResponseMessage();
        RoleResponse roleResponse = roleService.updateRole(roleRequest, roleName);

        if(roleResponse == null)
        {
            message.setStatus(HttpStatus.CONFLICT.value());
            message.setMessage("Failed to update roles");
            return ResponseEntity.status(HttpStatus.CONFLICT.value()).body(message);
        }
        else
        {
            message.setStatus(HttpStatus.OK.value());
            message.setMessage("Update role with id : " + roleResponse.getRoleId());
            message.setData(roleResponse);
            return ResponseEntity.ok().body(message);
        }
    }

    @DeleteMapping(value = "/delete/{roleName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseMessage> deleteRole(@PathVariable String roleName)
    {
        ResponseMessage message = new ResponseMessage();
        Boolean deleteRole = roleService.deleteRole(roleName);
        if(deleteRole)
        {
            message.setMessage("Success delete role by name : " + roleName);
            message.setStatus(HttpStatus.OK.value());
            return ResponseEntity.ok().body(message);
        }
        else
        {
            message.setMessage("Failed delete role by name : " + roleName + ", not found");
            message.setStatus(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(message);
        }
    }
}

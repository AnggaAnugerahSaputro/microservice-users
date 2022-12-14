package com.binar.microservice.userservice.repository;

import com.binar.microservice.userservice.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    @Query("SELECT u FROM User u WHERE LOWER(u.fullName) LIKE LOWER(:fullName)")
    User findByName(@Param("fullName") String fullName);
    @Query("SELECT u FROM User u WHERE LOWER(u.email) = LOWER(:email)")
    User findByEmail(@Param("email") String email);
    @Query("SELECT u FROM User u WHERE (u.telephone) = (:telephone)")
    User findPhoneNumber(@Param("telephone") String telephone);
}

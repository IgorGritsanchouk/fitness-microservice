package com.fitness.userservice.dto;

import com.fitness.userservice.model.UserRole;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class UserResponse {

    private Long id;
    private String keycloakId;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private boolean enabled;
    private Timestamp createdAt;  //LocalDateTime
    private Timestamp updatedAt;  // LocalDateTime

}

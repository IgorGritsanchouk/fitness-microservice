package com.fitness.gateway.user;

import lombok.Data;
import java.sql.Timestamp;
//import com.fitness.userservice.model.UserRole;

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

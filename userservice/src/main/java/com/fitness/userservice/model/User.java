package com.fitness.userservice.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
//import org.springframework.data.annotation.Id;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

@Table(name = "users")
@NoArgsConstructor
@Data
public class User {

    @Id
    @NotBlank(message = "Id is required.")
    private Long id;

    @NotBlank(message = "Email is required.")
    private String email;

    private String keycloakId;

    @NotBlank(message = "Password is required.")
    @Size(min = 7, max = 16, message = "Password must be between 7 and 16 characters")
    private String password;

    @NotBlank(message = "First name is required.")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;

    @NotBlank(message = "Last name is required.")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    private String lastName;

    private boolean enabled;

    private UserRole roles;

    //private UserRole role= UserRole.USER;

    private Timestamp createdAt;  //LocalDateTime

    private Timestamp updatedAt;  // LocalDateTime

    public void setCreatedAt(Date createdAt){
        this.createdAt= (createdAt != null) ? new Timestamp(createdAt.getTime()) : new Timestamp(System.currentTimeMillis());
    }
    public void setUpdatedAt(Date updatedAt){
        this.updatedAt= (updatedAt != null) ? new Timestamp(updatedAt.getTime()) : new Timestamp(System.currentTimeMillis());
    }
//    public Timestamp getCreatedAt(){
//        return (createdAt != null) ? createdAt : new Timestamp(System.currentTimeMillis());
//    }
    public Date convertTimestampToDate(Timestamp timestamp){
        return new Date(timestamp.getTime());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(getId(), user.getId()) && Objects.equals(getEmail(), user.getEmail()) && Objects.equals(getPassword(), user.getPassword()) && Objects.equals(getFirstName(), user.getFirstName()) && Objects.equals(getLastName(), user.getLastName()) && getRoles() == user.getRoles() && Objects.equals(getCreatedAt(), user.getCreatedAt()) && Objects.equals(getUpdatedAt(), user.getUpdatedAt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getEmail(), getPassword(), getFirstName(), getLastName(), getRoles(), getCreatedAt(), getUpdatedAt());
    }

}

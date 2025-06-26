package com.fitness.userservice.servise;

import com.fitness.userservice.controller.UserController;
import com.fitness.userservice.dto.RegisterRequest;
import com.fitness.userservice.dto.UserResponse;
import com.fitness.userservice.model.User;
import com.fitness.userservice.model.UserRole;
import com.fitness.userservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {

    private static final Logger logger= LoggerFactory.getLogger(UserService.class);

    private UserRepository userRepository;

    public UserResponse register(RegisterRequest request) {

        if(userRepository.existsByEmail(request.getEmail())){
            //throw new RuntimeException("Email already exist: "+ request.getEmail());
            User existingUser= userRepository.findByEmail(request.getEmail());
            UserResponse userResponse= new UserResponse();
            userResponse.setId(existingUser.getId());
            userResponse.setPassword(existingUser.getPassword());
            userResponse.setKeycloakId(existingUser.getKeycloakId());
            userResponse.setEmail(existingUser.getEmail());
            userResponse.setFirstName(existingUser.getFirstName());
            userResponse.setLastName(existingUser.getLastName());
            userResponse.setCreatedAt(existingUser.getCreatedAt());
            userResponse.setUpdatedAt(existingUser.getUpdatedAt());
            userResponse.setEnabled(existingUser.isEnabled());
            return userResponse;
        }

       User user= new User();
       user.setEmail(request.getEmail());
       user.setPassword(request.getPassword());
       user.setKeycloakId(request.getKeycloakId());
       user.setFirstName(request.getFirstName());
       user.setLastName(request.getLastName());
       user.setRoles(UserRole.USER_ROLE);
       user.setEnabled(true);
       user.setCreatedAt(new java.sql.Date(System.currentTimeMillis()));
       user.setUpdatedAt(new java.sql.Date(System.currentTimeMillis()));

       User savedUser= userRepository.save(user);
       UserResponse userResponse= new UserResponse();
       userResponse.setId(savedUser.getId());
       userResponse.setPassword(savedUser.getPassword());
       userResponse.setKeycloakId(savedUser.getKeycloakId());
       userResponse.setEmail(savedUser.getEmail());
       userResponse.setFirstName(savedUser.getFirstName());
       userResponse.setLastName(savedUser.getLastName());
       userResponse.setCreatedAt(savedUser.getCreatedAt());
       userResponse.setUpdatedAt(savedUser.getUpdatedAt());
       userResponse.setEnabled(savedUser.isEnabled());

       return userResponse;
    }

    public UserResponse getUserProfile(Long userId) {

        User user= userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User Not Found id: "+ userId));

        UserResponse userResponse= new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setPassword(user.getPassword());
        userResponse.setEmail(user.getEmail());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setCreatedAt(user.getCreatedAt());
        userResponse.setUpdatedAt(user.getUpdatedAt());
        userResponse.setEnabled(user.isEnabled());

        logger.info("Returning UserResponse for id: "+ userResponse.getId());
        return userResponse;
    }

    public Boolean existByUserId(String userId) {
        log.info("Calling User Validation API for userId: {}", userId);
        //return userRepository.existsById(Long.valueOf(userId));
        return userRepository.existsByKeycloakId(userId);
    }
}

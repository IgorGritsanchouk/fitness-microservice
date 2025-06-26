package com.fitness.userservice.controller;

import com.fitness.userservice.dto.RegisterRequest;
import com.fitness.userservice.dto.UserResponse;
import com.fitness.userservice.servise.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/apiv1/users")
@AllArgsConstructor
public class UserController {
    //private static final Logger logger= LoggerFactory.getLogger(UserController.class);

    private UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserProfile(@PathVariable String userId){

        log.info("Getting User with userId: "+ userId );
        return ResponseEntity.ok(userService.getUserProfile(Long.valueOf(userId)));
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest request){

        log.info("Register User with userId: "); //+ request.getEmail());
        return ResponseEntity.ok(userService.register(request));
    }

    @GetMapping("/{userId}/validate")
    public ResponseEntity<Boolean> validateUser(@PathVariable String userId){

        log.info("Validate User with userId: "+ userId );
        return ResponseEntity.ok(userService.existByUserId(userId));
    }

}

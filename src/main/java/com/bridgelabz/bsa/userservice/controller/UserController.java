package com.bridgelabz.bsa.userservice.controller;


import com.bridgelabz.bsa.userservice.requestdto.LoginRequest;
import com.bridgelabz.bsa.userservice.requestdto.RegistrationRequest;
import com.bridgelabz.bsa.userservice.requestdto.UserRequest;
import com.bridgelabz.bsa.userservice.responsedto.LoginResponse;
import com.bridgelabz.bsa.userservice.responsedto.UserResponse;
import com.bridgelabz.bsa.userservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @PostMapping("/register/users")
    public ResponseEntity<UserResponse> registerUser(@RequestBody RegistrationRequest registrationRequest) {
        UserResponse userResponse =  userService.registerUser(registrationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

    @PostMapping("/login/users")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse response = userService.login(loginRequest);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<UserResponse> findUserById(@PathVariable long userId) {
        UserResponse userResponse =  userService.findUserById(userId);
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> findAllUsers() {
        List<UserResponse> userResponses =  userService.findAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(userResponses);
    }

    @PutMapping("/users/{userId}")
    public ResponseEntity<UserResponse> updateUserById(@PathVariable long userId, @RequestBody UserRequest userRequest) {
        UserResponse userResponse = userService.updateUserById(userId, userRequest);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userResponse);
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<UserResponse> deleteUserById(@PathVariable long userId) {
        UserResponse userResponse = userService.deleteUserById(userId);
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    @GetMapping("/users/validate-token")
    public ResponseEntity<Long> validateJwtToken(@RequestHeader("Authorization") String authToken) {
        String token = authToken.substring(7);
        return ResponseEntity.status(HttpStatus.OK).body(userService.validateJwtToken(token));
    }


}



package com.bridgelabz.bsa.userservice.mapper;


import com.bridgelabz.bsa.userservice.model.User;
import com.bridgelabz.bsa.userservice.requestdto.RegistrationRequest;
import com.bridgelabz.bsa.userservice.requestdto.UserRequest;
import com.bridgelabz.bsa.userservice.responsedto.UserResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@AllArgsConstructor
public class UserMapper {

    private BCryptPasswordEncoder encoder;

    public User mapToUser(UserRequest userRequest, User user) {
        user.setFname(userRequest.getFname());
        user.setLname(userRequest.getLname());
        user.setDob(userRequest.getDob());
        user.setRole(userRequest.getRole());
        return user;
    }

    public User mapToUser(RegistrationRequest registrationRequest) {
        User user = new User();
        user.setFname(registrationRequest.getFname());
        user.setLname(registrationRequest.getLname());
        user.setEmail(registrationRequest.getEmail());
        user.setDob(registrationRequest.getDob());
        user.setRole(registrationRequest.getRole());
        user.setPassword(encoder.encode(registrationRequest.getPassword()));
        user.setRegisteredDate(LocalDate.now());
        return user;
    }
    public UserResponse mapToUserResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setUserId(user.getUserId());
        userResponse.setFname(user.getFname());
        userResponse.setLname(user.getLname());
        userResponse.setEmail(user.getEmail());
        userResponse.setDob(user.getDob());
        userResponse.setRole(user.getRole());
        userResponse.setRegisteredDate(user.getRegisteredDate());
        userResponse.setUpdatedDate(user.getUpdatedDate());
        return userResponse;

    }
}

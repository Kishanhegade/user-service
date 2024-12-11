package com.bridgelabz.bsa.userservice.service;


import com.bridgelabz.bsa.userservice.exception.UserNotFoundByIdException;
import com.bridgelabz.bsa.userservice.mapper.UserMapper;
import com.bridgelabz.bsa.userservice.model.User;
import com.bridgelabz.bsa.userservice.repository.UserRepository;
import com.bridgelabz.bsa.userservice.requestdto.LoginRequest;
import com.bridgelabz.bsa.userservice.requestdto.RegistrationRequest;
import com.bridgelabz.bsa.userservice.requestdto.UserRequest;
import com.bridgelabz.bsa.userservice.responsedto.LoginResponse;
import com.bridgelabz.bsa.userservice.responsedto.UserResponse;
import com.bridgelabz.bsa.userservice.security.JwtUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;
    private UserMapper userMapper;
    private JwtUtils jwtUtils;
    private AuthenticationManager authenticationManager;
    private UserDetailsService userDetailsService;

    public UserResponse registerUser(RegistrationRequest registrationRequest) {
        User user = userMapper.mapToUser(registrationRequest);
        user = userRepository.save(user);
        return userMapper.mapToUserResponse(user);
    }

    public LoginResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(()->
                new UsernameNotFoundException("username not found"));

        String token = jwtUtils.generateTokenFromUsername(user);
        LoginResponse response = new LoginResponse();
        response.setEmail(user.getEmail());
        response.setJwtToken(token);
        return response;
    }

    public UserResponse findUserById(long userId) {
        return userRepository.findById(userId)
                .map(user -> userMapper.mapToUserResponse(user))
                .orElseThrow(()->new UserNotFoundByIdException("Failed to find user"));
    }

    public List<UserResponse> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(user->userMapper.mapToUserResponse(user)).toList();
    }

    public UserResponse updateUserById(long userId, UserRequest userRequest) {
        return userRepository.findById(userId)
                .map(user ->{
                    user = userMapper.mapToUser(userRequest,user);
                    user.setUpdatedDate(LocalDate.now());
                    userRepository.save(user);
                    return userMapper.mapToUserResponse(user);
                }).orElseThrow(() -> new UserNotFoundByIdException("Failed to update user"));
    }

    public UserResponse deleteUserById(long userId) {

        return userRepository.findById(userId)
                .map(user ->{
                    userRepository.deleteById(userId);
                    return userMapper.mapToUserResponse(user);
                }).orElseThrow(() -> new UserNotFoundByIdException("Failed to update user"));
    }

    public Long validateJwtToken(String token) {
        String username = jwtUtils.extractUsernameFromToken(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if(jwtUtils.validateJwtToken(token,userDetails)){
            return jwtUtils.extractUserIdFromToken(token);
        }
        return null;
    }

}

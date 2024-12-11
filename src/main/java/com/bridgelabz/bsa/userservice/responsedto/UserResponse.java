package com.bridgelabz.bsa.userservice.responsedto;

import com.bridgelabz.bsa.userservice.model.Role;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserResponse {

    private Long userId;
    private String fname;
    private String lname;
    private String email;
    private LocalDate dob;
    private Role role;
    private LocalDate registeredDate;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDate updatedDate;
}

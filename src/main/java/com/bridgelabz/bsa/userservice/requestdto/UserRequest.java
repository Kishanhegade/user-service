package com.bridgelabz.bsa.userservice.requestdto;

import com.bridgelabz.bsa.userservice.model.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserRequest {

    @NotBlank(message = "First Name is mandatory")
    private String fname;

    @NotBlank(message = "Last Name is mandatory")
    private String lname;

    @Past(message = "Date of Birth must be in the past")
    @NotBlank(message = "Date of Birth is mandatory")
    private LocalDate dob;

    @NotBlank(message = "Role is mandatory")
    private Role role;

}

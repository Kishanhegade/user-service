package com.bridgelabz.bsa.userservice.responsedto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
    private String jwtToken;
    private String email;
}

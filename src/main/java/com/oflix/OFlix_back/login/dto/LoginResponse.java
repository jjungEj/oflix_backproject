package com.oflix.OFlix_back.login.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private int status;
    private String errorCode;
    private String message;
    private String username;
    private String role;
}

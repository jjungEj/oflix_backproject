package com.oflix.OFlix_back.login.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse {
    private int status;
    private String errorCode;
    private String message;
    private String username;
    private String role;
}

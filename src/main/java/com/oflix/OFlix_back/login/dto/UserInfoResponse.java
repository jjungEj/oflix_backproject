package com.oflix.OFlix_back.login.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoResponse {
    private int status;
    private String errorCode;
    private String message;
    private String username;
    private String role;
    private String nickname;
    private String phoneNumber;

}


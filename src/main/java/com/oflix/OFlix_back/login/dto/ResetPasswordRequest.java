package com.oflix.OFlix_back.login.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordRequest {
    private String username;
    private String phoneNumber;
    private String nickname;
    private String newPassword;
}
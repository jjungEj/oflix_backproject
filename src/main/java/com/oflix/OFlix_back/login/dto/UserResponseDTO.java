package com.oflix.OFlix_back.login.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponseDTO {
    private String username;
    private String nickname;
    private String phoneNumber;
    private String role;
}

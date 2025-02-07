package com.oflix.OFlix_back.login.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class JoinDTO {

    private String nickname;

    private String username;

    private String password;

    private String role;

    private String phoneNumber;

    private LocalDate birthDate;

}

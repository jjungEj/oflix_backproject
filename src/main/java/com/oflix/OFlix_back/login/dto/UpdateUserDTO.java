package com.oflix.OFlix_back.login.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateUserDTO {
    private String nickname;
    private String phoneNumber;


}

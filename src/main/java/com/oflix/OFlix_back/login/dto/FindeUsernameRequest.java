package com.oflix.OFlix_back.login.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindeUsernameRequest {
    private String phoneNumber;
    private String nickname;
}

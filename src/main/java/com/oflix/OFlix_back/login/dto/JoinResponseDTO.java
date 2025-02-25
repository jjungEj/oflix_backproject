
package com.oflix.OFlix_back.login.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JoinResponseDTO {
    private String message;
    private String username;
    private String nickname;
    private String phoneNumber;
}


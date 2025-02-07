package com.oflix.OFlix_back.login.controller;

import com.oflix.OFlix_back.login.dto.JoinDTO;
import com.oflix.OFlix_back.login.service.JoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class JoinController {

    private final JoinService joinService;

    @PostMapping("/join")
    public ResponseEntity<String> signUpProcess(JoinDTO joinDTO) {

        boolean signUpSuccess = joinService.signUpProcess(joinDTO);

        if (!signUpSuccess) {
            return new ResponseEntity<>("Email already exists", HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>("User created successfully", HttpStatus.CREATED);
    }
}

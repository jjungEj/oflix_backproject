package com.oflix.OFlix_back.login.controller;

import com.oflix.OFlix_back.login.dto.*;
import com.oflix.OFlix_back.login.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;



    @GetMapping("/userInfo")
    public ResponseEntity<UserInfoResponse> getUserInfo(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return userService.getUserInfo(userDetails);
    }


    @PostMapping("/join")
    public ResponseEntity<JoinResponseDTO> signUpProcess(@RequestBody UserDTO userDTO) {
        return userService.signUpProcess(userDTO);
    }

    @PutMapping("/update")
    public ResponseEntity<UserInfoResponse> updateUser(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                       @RequestBody UpdateUserDTO updateUserDTO) {
        return userService.updateUser(userDetails, updateUserDTO);
    }


    @DeleteMapping("/{username}")
    public ResponseEntity<String> deleteUser(@PathVariable String username) {
        return userService.deleteUser(username);
    }
}



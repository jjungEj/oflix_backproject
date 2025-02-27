package com.oflix.OFlix_back.login.controller;

import com.oflix.OFlix_back.login.dto.*;
import com.oflix.OFlix_back.login.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
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


    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestParam String username, HttpServletResponse response) {
        userService.deleteUser(username);

        // 쿠키 삭제
        Cookie refreshCookie = new Cookie("refresh", null);
        refreshCookie.setMaxAge(0);
        refreshCookie.setPath("/");

        Cookie accessCookie = new Cookie("access", null);
        accessCookie.setMaxAge(0);
        accessCookie.setPath("/");

        response.addCookie(refreshCookie);
        response.addCookie(accessCookie);

        return ResponseEntity.ok("회원 탈퇴가 완료되었습니다.");
    }

}



package com.oflix.OFlix_back.login.controller;

import com.oflix.OFlix_back.login.dto.FindeUsernameRequest;
import com.oflix.OFlix_back.login.dto.ResetPasswordRequest;
import com.oflix.OFlix_back.login.service.FindService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/find")
public class FindController {

    private final FindService findService;

    // 아이디 찾기 API
    @PostMapping("/find-username")
    public ResponseEntity<String> findUsername(@RequestBody FindeUsernameRequest request) {
        String username = findService.findUsername(request.getPhoneNumber(), request.getNickname());
        return ResponseEntity.ok("ID : " + username);
    }

    // 비밀번호 찾기 API
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest request) {
        String responseMessage = findService.changePassword(
                request.getUsername(),
                request.getPhoneNumber(),
                request.getNickname(),
                request.getNewPassword() // 새 비밀번호 전달
        );
        return ResponseEntity.ok(responseMessage);
    }
}

package com.oflix.OFlix_back.login.controller;

import com.oflix.OFlix_back.login.dto.UserDTO;
import com.oflix.OFlix_back.login.dto.JoinResponseDTO;
import com.oflix.OFlix_back.login.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;


    @PostMapping("/join")
    public ResponseEntity<JoinResponseDTO> signUpProcess(@RequestBody UserDTO userDTO) {
        return userService.signUpProcess(userDTO);
    }

    @GetMapping("/{username}")
    public ResponseEntity<JoinResponseDTO> getUser(@PathVariable String username) {
        return userService.getUserByUsername(username);
    }

    @PutMapping("/{username}")
    public ResponseEntity<JoinResponseDTO> updateUser(
            @PathVariable String username,
            @RequestBody UserDTO updateDTO
    ) {
        return userService.updateUser(username, updateDTO);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<String> deleteUser(@PathVariable String username) {
        return userService.deleteUser(username);
    }
}



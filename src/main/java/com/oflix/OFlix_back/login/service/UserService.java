package com.oflix.OFlix_back.login.service;

import com.oflix.OFlix_back.login.dto.*;
import com.oflix.OFlix_back.login.entity.User;
import com.oflix.OFlix_back.login.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 회원가입 (Create)
     */
    public ResponseEntity<JoinResponseDTO> signUpProcess(UserDTO userDTO) {
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new JoinResponseDTO("Username already exists", null, null, null));
        }

        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        user.setNickname(userDTO.getNickname());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setRole("ROLE_USER");

        userRepository.save(user);

        return ResponseEntity.ok(
                new JoinResponseDTO("User created successfully",
                        user.getUsername(),
                        user.getNickname(),
                        user.getPhoneNumber()
                )
        );
    }


    public ResponseEntity<UserInfoResponse> getUserInfo(CustomUserDetails userDetails) {
        String username = (userDetails != null) ? userDetails.getUsername() : null;
        String authority = (userDetails != null && !userDetails.getAuthorities().isEmpty())
                ? userDetails.getAuthorities().iterator().next().getAuthority()
                : null;

        String nickname = (userDetails != null) ? userDetails.getNickname() : null;
        String phoneNumber = (userDetails != null) ? userDetails.getPhoneNumber() : null;

        UserInfoResponse response = new UserInfoResponse(200, "SUCCESS", "User info retrieved successfully",
                username, authority, nickname, phoneNumber);

        return ResponseEntity.ok(response);
    }



    public ResponseEntity<UserInfoResponse> updateUser(CustomUserDetails userDetails, UpdateUserDTO updateUserDTO) {
        // 유저 인증 정보로 유저를 찾기
        String username = userDetails.getUsername();
        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new UserInfoResponse(404, "User not found", "User not found", null, null, null, null));
        }

        User user = optionalUser.get();

        // 유저 정보 업데이트
        if (updateUserDTO.getNickname() != null) {
            user.setNickname(updateUserDTO.getNickname());
        }
        if (updateUserDTO.getPhoneNumber() != null) {
            user.setPhoneNumber(updateUserDTO.getPhoneNumber());
        }


        userRepository.save(user);

        // 업데이트된 유저 정보 응답
        UserInfoResponse response = new UserInfoResponse(
                200,
                "SUCCESS",
                "User info updated successfully",
                user.getUsername(),
                user.getRole(),
                user.getNickname(),
                user.getPhoneNumber()
        );

        return ResponseEntity.ok(response);
    }


    @Transactional
    public void deleteUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        userRepository.delete(user);
    }
}

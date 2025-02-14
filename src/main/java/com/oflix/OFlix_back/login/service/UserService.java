package com.oflix.OFlix_back.login.service;

import com.oflix.OFlix_back.login.dto.UserDTO;
import com.oflix.OFlix_back.login.dto.JoinResponseDTO;
import com.oflix.OFlix_back.login.entity.UserEntity;
import com.oflix.OFlix_back.login.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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

        UserEntity user = new UserEntity();
        user.setUsername(userDTO.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        user.setNickname(userDTO.getNickname());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setRole("ROLE_ADMIN");
        user.setBirthDate(userDTO.getBirthDate());

        userRepository.save(user);

        return ResponseEntity.ok(
                new JoinResponseDTO("User created successfully",
                        user.getUsername(),
                        user.getNickname(),
                        user.getPhoneNumber())
        );
    }


    public ResponseEntity<JoinResponseDTO> getUserByUsername(String username) {
        Optional<UserEntity> user = userRepository.findByUsername(username);

        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new JoinResponseDTO("User not found", null, null, null));
        }

        UserEntity foundUser = user.get();
        return ResponseEntity.ok(
                new JoinResponseDTO("User found",
                        foundUser.getUsername(),
                        foundUser.getNickname(),
                        foundUser.getPhoneNumber())
        );
    }

    /**
     * 회원 정보 수정 (Update)
     */
    public ResponseEntity<JoinResponseDTO> updateUser(String username, UserDTO updateDTO) {
        Optional<UserEntity> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new JoinResponseDTO("User not found", null, null, null));
        }

        UserEntity user = optionalUser.get();
        user.setNickname(updateDTO.getNickname());
        user.setPhoneNumber(updateDTO.getPhoneNumber());

        // 비밀번호가 입력되었으면 업데이트 (선택사항)
        if (updateDTO.getPassword() != null && !updateDTO.getPassword().isEmpty()) {
            user.setPassword(bCryptPasswordEncoder.encode(updateDTO.getPassword()));
        }

        userRepository.save(user);

        return ResponseEntity.ok(
                new JoinResponseDTO("User updated successfully",
                        user.getUsername(),
                        user.getNickname(),
                        user.getPhoneNumber())
        );
    }

    /**
     * 회원 삭제 (Delete)
     */
    public ResponseEntity<String> deleteUser(String username) {
        Optional<UserEntity> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"message\": \"User not found\"}");
        }

        userRepository.delete(optionalUser.get());
        return ResponseEntity.ok("{\"message\": \"User deleted successfully\"}");
    }
}

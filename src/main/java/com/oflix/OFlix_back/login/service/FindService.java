package com.oflix.OFlix_back.login.service;

import com.oflix.OFlix_back.login.entity.User;
import com.oflix.OFlix_back.login.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FindService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    // 아이디 찾기 (전화번호 + 닉네임 기반)
    public String findUsername(String phoneNumber, String nickname) {
        Optional<User> user = userRepository.findByPhoneNumberAndNickname(phoneNumber, nickname);
        return user.map(User::getUsername)
                .orElseThrow(() -> new RuntimeException("해당 정보를 가진 사용자를 찾을 수 없습니다."));
    }

    @Transactional
    public String changePassword(String username, String phoneNumber, String nickname, String newPassword) {
        User user = userRepository.findByUsernameAndPhoneNumberAndNickname(username, phoneNumber, nickname)
                .orElseThrow(() -> new RuntimeException("해당 정보를 가진 사용자를 찾을 수 없습니다."));

        // 입력한 새 비밀번호를 암호화하여 저장
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        return "비밀번호가 성공적으로 변경되었습니다.";
    }
}

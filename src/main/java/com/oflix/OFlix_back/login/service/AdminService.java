
package com.oflix.OFlix_back.login.service;

import com.oflix.OFlix_back.login.entity.User;
import com.oflix.OFlix_back.login.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;

    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);  // Pageable에 맞춰 페이지네이션된 유저 목록 반환
    }

    public void deleteUser(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        // 현재 'admin' 권한을 가진 유저 수 확인
        long adminCount = userRepository.countByRole("ROLE_ADMIN");

        // 마지막 남은 관리자인 경우 삭제 방지
        if (user.getRole().equals("ROLE_ADMIN") && adminCount <= 1) {
            throw new RuntimeException("최소 한 명의 관리자가 필요합니다. 삭제할 수 없습니다.");
        }

        userRepository.delete(user);
    }


    public void updateUserRole(Integer userId, String newRole) {
        // 사용자가 존재하는지 확인
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        newRole = newRole.replace("\"", "");
        // 권한 수정
        user.setRole(newRole);
        userRepository.save(user); // 변경 사항 저장
    }
}

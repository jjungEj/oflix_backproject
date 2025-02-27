package com.oflix.OFlix_back.login.controller;

import com.oflix.OFlix_back.login.entity.User;
import com.oflix.OFlix_back.login.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/alluser")
    public ResponseEntity<Page<User>> getAllUsers(
            @RequestParam(defaultValue = "username") String sortBy,  // 정렬 기준 (기본값: username)
            @RequestParam(defaultValue = "asc") String direction,  // 정렬 방향 (기본값: asc)
            @RequestParam(defaultValue = "0") int page,  // 페이지 번호 (기본값: 0)
            @RequestParam(defaultValue = "10") int size  // 페이지 크기 (기본값: 10)
    ) {
        Pageable sortedPageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sortBy));
        Page<User> users = adminService.getAllUsers(sortedPageable);
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();  // 데이터가 없으면 204 상태 코드 반환
        }
        return ResponseEntity.ok(users);  // 페이지네이션된 유저 목록 반환
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer id) {
        try {
            adminService.deleteUser(id);
            return ResponseEntity.ok("사용자가 성공적으로 삭제되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(400).body("사용자 삭제에 실패하였습니다: " + e.getMessage());
        }
    }

    // 권한 수정 API 추가
    @PutMapping("/users/{id}/role")
    public ResponseEntity<String> updateUserRole(@PathVariable Integer id, @RequestBody String newRole) {
        try {
            adminService.updateUserRole(id, newRole);
            return ResponseEntity.ok("사용자의 권한이 성공적으로 변경되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(400).body("권한 변경에 실패하였습니다: " + e.getMessage());
        }
    }
}

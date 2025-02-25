package com.oflix.OFlix_back.login.controller;

import com.oflix.OFlix_back.login.entity.User;
import com.oflix.OFlix_back.login.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/alluser")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = adminService.getAllUsers();
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(users);
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

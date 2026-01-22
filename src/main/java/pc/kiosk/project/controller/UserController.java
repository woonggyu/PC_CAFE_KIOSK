package pc.kiosk.project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pc.kiosk.project.dto.request.LoginRequestDTO;
import pc.kiosk.project.dto.request.UserSaveRequestDTO;
import pc.kiosk.project.dto.response.UserResponseDTO;
import pc.kiosk.project.service.UserService;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원가입 API (POST http://localhost:8080/api/users/join)
    @PostMapping("/join")
    public ResponseEntity<Long> join(@RequestBody UserSaveRequestDTO dto) {
        return ResponseEntity.ok(userService.join(dto));
    }

    // 로그인 API (POST http://localhost:8080/api/users/login)
    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> login(@RequestBody LoginRequestDTO dto) {
        return ResponseEntity.ok(userService.login(dto));
    }
}
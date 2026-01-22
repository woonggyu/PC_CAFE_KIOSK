package pc.kiosk.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pc.kiosk.project.dto.request.LoginRequestDTO;
import pc.kiosk.project.dto.request.UserSaveRequestDTO;
import pc.kiosk.project.dto.response.UserResponseDTO;
import pc.kiosk.project.entity.Seat;
import pc.kiosk.project.entity.User;
import pc.kiosk.project.repository.SeatRepository;
import pc.kiosk.project.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final SeatRepository seatRepository;

    /**
     * 회원가입
     */
    @Transactional
    public Long join(UserSaveRequestDTO dto) {
        User user = new User();
        user.setUserId(dto.getUserId());
        user.setUserPw(dto.getUserPw());
        user.setName(dto.getName());
        return userRepository.save(user).getId();
    }

    /**
     * 로그인 (실제 SEAT 테이블 정보 조회 및 사용 처리)
     */
    @Transactional
    public UserResponseDTO login(LoginRequestDTO dto) {
        // 1. 유저 인증 (아이디/비번 확인)
        User user = userRepository.findByUserId(dto.getUserId())
                .filter(u -> u.getUserPw().equals(dto.getUserPw()))
                .orElseThrow(() -> new IllegalArgumentException("아이디 또는 비밀번호가 틀립니다."));

        // 2. 좌석 정보 실시간 조회 (DB의 SEAT 테이블에서 가져옴)
        // 리액트에서 전달된 dto.getSeatNo() 값을 기준으로 찾습니다.
        Seat seat = seatRepository.findBySeatNo((long) dto.getSeatNo())
                .orElseThrow(() -> new IllegalArgumentException(dto.getSeatNo() + "번 좌석은 존재하지 않습니다."));

        // 3. 좌석 상태 업데이트 (비어있는지 확인 로직은 상황에 따라 추가 가능)
        seat.setUserId(user.getUserId()); // DB의 USER_ID 컬럼 업데이트
        seat.setStatus("USING");         // DB의 STATUS 컬럼을 'USING'으로 변경

        // 💡 JpaRepository의 dirty checking으로 인해 save를 생략해도 되지만 명시적으로 호출
        seatRepository.save(seat);

        // 4. 응답 생성 (DB에서 확인된 진짜 정보를 담아줌)
        UserResponseDTO response = new UserResponseDTO();
        response.setUserId(user.getUserId());
        response.setName(user.getName());
        response.setRemainTime(user.getRemainTime());
        response.setRole(user.getRole());

        // 💡 중요: 리액트가 화면에 띄울 "진짜 좌석 번호"를 DB 데이터에서 꺼내서 전달
        response.setSeatNo(seat.getSeatNo().intValue());

        return response;
    }
}
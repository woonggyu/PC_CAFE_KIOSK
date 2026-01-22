package pc.kiosk.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pc.kiosk.project.dto.response.SeatResponseDTO;
import pc.kiosk.project.entity.Seat;
import pc.kiosk.project.entity.User;
import pc.kiosk.project.repository.SeatRepository;
import pc.kiosk.project.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SeatService {

    private final SeatRepository seatRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<SeatResponseDTO> getAllSeats() {
        return seatRepository.findAllByOrderBySeatNoAsc()
                .stream()
                .map(seat -> {
                    SeatResponseDTO dto = new SeatResponseDTO(seat);
                    if (seat.getUserId() != null) {
                        userRepository.findByUserId(seat.getUserId())
                                .ifPresent(user -> dto.setRemainTime(user.getRemainTime()));
                    }
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public SeatResponseDTO updateSeat(Long seatNo, String status, Integer addTime, String userId) { // 👈 userId 파라미터 추가
        Seat seat = seatRepository.findById(seatNo)
                .orElseThrow(() -> new RuntimeException("좌석을 찾을 수 없습니다."));

        // 1. 좌석 켜기 로직 (USING으로 변경 시 아이디 등록)
        if ("USING".equals(status)) {
            seat.setStatus("USING");
            if (userId != null && !userId.isEmpty()) {
                // 관리자가 입력한 아이디가 실제 존재하는 유저인지 확인
                userRepository.findByUserId(userId)
                        .orElseThrow(() -> new RuntimeException("존재하지 않는 회원 아이디입니다."));
                seat.setUserId(userId);
            } else if (seat.getUserId() == null) {
                // 아이디 없이 켜려고 할 때 방어 로직
                throw new RuntimeException("사용 중으로 변경하려면 사용자 아이디가 필요합니다.");
            }
        }

        // 2. 시간 추가 로직 (사용자가 있는 상태에서만 가능)
        if (addTime != null && addTime > 0) {
            String currentUserId = seat.getUserId();
            if (currentUserId != null) {
                User user = userRepository.findByUserId(currentUserId)
                        .orElseThrow(() -> new RuntimeException("사용자 정보를 찾을 수 없습니다."));
                user.setRemainTime(user.getRemainTime() + addTime);
            } else {
                throw new RuntimeException("사용자가 없는 좌석에는 시간을 추가할 수 없습니다.");
            }
        }

        // 3. 상태 변경 (EMPTY 처리 시 좌석 비우기)
        if ("EMPTY".equals(status)) {
            seat.clearSeat(); // userId도 null이 됨
        }

        // 4. 최신 정보 반환
        SeatResponseDTO response = new SeatResponseDTO(seat);
        if (seat.getUserId() != null) {
            userRepository.findByUserId(seat.getUserId())
                    .ifPresent(user -> response.setRemainTime(user.getRemainTime()));
        }
        return response;
    }
}
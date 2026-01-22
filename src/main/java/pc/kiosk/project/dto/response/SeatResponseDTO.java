package pc.kiosk.project.dto.response;

import pc.kiosk.project.entity.Seat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class SeatResponseDTO {
    private Long seatNo;      // 좌석 번호
    private String status;    // 사용 여부 (EMPTY, USING)
    private String userId;    // 사용자 아이디
    private Integer remainTime; // 남은 시간

    // 엔티티(Seat)를 DTO로 편하게 변환하기 위한 생성자
    public SeatResponseDTO(Seat seat) {
        this.seatNo = seat.getSeatNo();
        this.status = seat.getStatus();
        this.userId = seat.getUserId();

    }
}

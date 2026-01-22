package pc.kiosk.project.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SeatRequestDTO {
    private String status;    // 바꿀 상태 (예: "USING" 또는 "EMPTY")
    private Integer addTime;  // 추가할 시간 (분 단위)
    private String userId;    // (선택) 수동으로 사용자를 지정할 경우
}

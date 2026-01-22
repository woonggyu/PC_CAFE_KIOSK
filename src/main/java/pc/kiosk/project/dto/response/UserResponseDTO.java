package pc.kiosk.project.dto.response;

import lombok.Getter;
import lombok.Setter;

/**
 * 로그인 성공 후 클라이언트에 반환할 사용자 정보 DTO
 */
@Getter
@Setter
public class UserResponseDTO {

    private String userId;     // 로그인 아이디
    private String name;       // 사용자 이름
    private int remainTime;    // PC방 잔여 시간 (분 단위)
    private String role;       //사용자 or 관리자
    private int seatNo;
    // 생성자나 빌더 패턴을 추가하여 Entity에서 DTO로의 변환을 편리하게 할 수 있습니다.
}
package pc.kiosk.project.dto.request;

import lombok.Getter;
import lombok.Setter;


// 로그인 요청
@Getter
@Setter
public class LoginRequestDTO {
    private String userId;
    private String userPw;
    private int seatNo;
}
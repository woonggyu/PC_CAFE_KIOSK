package pc.kiosk.project.dto.request;

import lombok.Getter;
import lombok.Setter;
//회원가입 요청
@Getter
@Setter
public class UserSaveRequestDTO {
    private String userId;
    private String userPw;
    private String name;
    private String phone;
}

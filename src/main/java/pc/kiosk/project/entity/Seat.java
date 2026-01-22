package pc.kiosk.project.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "SEATS")
@Getter @Setter
@NoArgsConstructor
public class Seat {

    @Id
    @Column(name = "seat_no") // 이미지의 소문자 seat_no와 매칭
    private Long seatNo;

    @Column(name = "STATUS") // 이미지의 대문자 STATUS와 매칭
    private String status = "EMPTY";

    @Column(name = "user_id") // 이미지의 소문자 user_id와 매칭
    private String userId;

    // 좌석 초기화 메서드
    public void clearSeat() {
        this.status = "EMPTY";
        this.userId = null;
    }

    // 좌석 사용 시작 메서드
    public void occupySeat(String userId) {
        this.status = "USING";
        this.userId = userId;
    }
}
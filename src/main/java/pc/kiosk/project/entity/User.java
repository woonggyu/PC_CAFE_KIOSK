package pc.kiosk.project.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "USERS") // MySQL에 만든 테이블 이름과 매칭
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // MySQL의 AUTO_INCREMENT 사용
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String userId;

    @Column(nullable = false, length = 255)
    private String userPw;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(columnDefinition = "INT DEFAULT 0")
    private int remainTime;

    @CreationTimestamp // 데이터가 생성될 때 자동으로 시간 입력
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false, length = 20)
    private String role = "USER"; // 기본값을 'USER'로 설정
}
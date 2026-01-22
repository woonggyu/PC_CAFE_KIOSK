package pc.kiosk.project.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@Table(name = "ORDERS")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Column(name = "USER_ID", nullable = false)
    private String userId; // 👈 사장님 요청대로 String 타입!

    @Column(nullable = false)
    private int seatNo;

    @Column(nullable = false)
    private int totalPrice;

    @Column(length = 20)
    private String orderStatus = "WAITING";

    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
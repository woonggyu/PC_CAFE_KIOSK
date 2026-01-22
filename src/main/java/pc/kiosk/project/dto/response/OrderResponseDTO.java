package pc.kiosk.project.dto.response;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;


//관리자, 사용자가 주문내열볼때 사용
@Getter @Setter
public class OrderResponseDTO {
    private Long orderId;
    private String userId;      // 👈 String 타입
    private int seatNo;
    private int totalPrice;
    private String orderStatus;
    private LocalDateTime createdAt;
    private List<OrderItemResponse> items;

    @Getter @Setter
    public static class OrderItemResponse {
        private String productName; // 💡 "신라면" 같은 이름이 여기에 들어갑니다.
        private int quantity;
        private int price;
    }
}
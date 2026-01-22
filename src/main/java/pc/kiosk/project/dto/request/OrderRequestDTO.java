package pc.kiosk.project.dto.request;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

//사용자가 주문할때 사용하는 DTO 장바구니에 정보담기

@Getter @Setter
public class OrderRequestDTO {
    private String userId;
    private int seatNo;
    private int totalPrice;
    private List<OrderItemRequest> items;

    @Getter @Setter
    public static class OrderItemRequest {
        private Long productId;
        private int quantity;
        private int price;
    }
}
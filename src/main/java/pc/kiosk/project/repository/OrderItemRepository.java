package pc.kiosk.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pc.kiosk.project.entity.OrderItem;
import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    // 💡 특정 주문 번호(orderId)에 속한 상세 아이템들 찾기
    List<OrderItem> findByOrderId(Long orderId);
}
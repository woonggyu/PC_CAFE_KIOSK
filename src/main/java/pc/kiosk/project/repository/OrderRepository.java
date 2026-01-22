package pc.kiosk.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pc.kiosk.project.entity.Order;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    // 💡 주문 상태(WAITING 등)에 따라 최신순으로 가져오기
    List<Order> findByOrderStatusOrderByCreatedAtDesc(String status);

    // 💡 특정 사용자(String userId)의 주문 내역만 보고 싶을 때
    List<Order> findByUserId(String userId);
}
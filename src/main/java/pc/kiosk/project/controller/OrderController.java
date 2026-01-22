package pc.kiosk.project.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pc.kiosk.project.dto.request.OrderRequestDTO;
import pc.kiosk.project.dto.response.OrderResponseDTO;
import pc.kiosk.project.service.OrderService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000") // 💡 리액트 포트 허용 필수!
public class OrderController {

    private final OrderService orderService;

    /**
     * 1. 사용자: 먹거리 주문하기
     * POST /api/orders
     */
    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody OrderRequestDTO requestDTO) {
        log.info("주문 요청 수신: userId={}, totalPrice={}", requestDTO.getUserId(), requestDTO.getTotalPrice());
        try {
            Long orderId = orderService.placeOrder(requestDTO);
            return ResponseEntity.ok("주문이 완료되었습니다. 주문번호: " + orderId);
        } catch (Exception e) {
            log.error("주문 처리 실패", e);
            return ResponseEntity.badRequest().body("주문 실패: " + e.getMessage());
        }
    }

    /**
     * 2. 관리자: 상태별 주문 목록 조회
     * GET /api/orders?status=WAITING
     */
    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getOrdersByStatus(
            @RequestParam(value = "status", defaultValue = "WAITING") String status) {

        List<OrderResponseDTO> orders = orderService.getOrdersByStatus(status);
        return ResponseEntity.ok(orders);
    }

    /**
     * 3. 관리자: 주문 상태 변경 (조리완료 등)
     * PATCH /api/orders/{orderId}/status?newStatus=COMPLETED
     */
    @PatchMapping("/{orderId}/status")
    public ResponseEntity<String> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam String newStatus) {

        try {
            orderService.updateOrderStatus(orderId, newStatus);
            return ResponseEntity.ok("상태가 " + newStatus + "(으)로 변경되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("상태 변경 실패: " + e.getMessage());
        }
    }
}
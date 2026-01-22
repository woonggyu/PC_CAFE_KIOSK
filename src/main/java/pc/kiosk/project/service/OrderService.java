package pc.kiosk.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pc.kiosk.project.dto.request.OrderRequestDTO;
import pc.kiosk.project.dto.response.OrderResponseDTO;
import pc.kiosk.project.entity.Order;
import pc.kiosk.project.entity.OrderItem;
import pc.kiosk.project.repository.OrderItemRepository;
import pc.kiosk.project.repository.OrderRepository;
import pc.kiosk.project.repository.ProductRepository; // 👈 추가

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository; // 👈 상품명을 가져오기 위해 추가

    /**
     * 1. 주문하기 (사용자용)
     */
    @Transactional
    public Long placeOrder(OrderRequestDTO dto) {
        Order order = new Order();
        order.setUserId(dto.getUserId());
        order.setSeatNo(dto.getSeatNo());
        order.setTotalPrice(dto.getTotalPrice());
        order.setOrderStatus("WAITING");

        Order savedOrder = orderRepository.save(order);

        List<OrderItem> items = dto.getItems().stream().map(itemDto -> {
            OrderItem item = new OrderItem();
            item.setOrderId(savedOrder.getOrderId());
            item.setProductId(itemDto.getProductId());
            item.setQuantity(itemDto.getQuantity());
            item.setPrice(itemDto.getPrice());
            return item;
        }).collect(Collectors.toList());

        orderItemRepository.saveAll(items);
        return savedOrder.getOrderId();
    }

    /**
     * 2. 상태별 주문 조회 (관리자용)
     */
    @Transactional(readOnly = true)
    public List<OrderResponseDTO> getOrdersByStatus(String status) {
        List<Order> orders = orderRepository.findByOrderStatusOrderByCreatedAtDesc(status);

        return orders.stream().map(order -> {
            OrderResponseDTO response = new OrderResponseDTO();
            response.setOrderId(order.getOrderId());
            response.setUserId(order.getUserId());
            response.setSeatNo(order.getSeatNo());
            response.setTotalPrice(order.getTotalPrice());
            response.setOrderStatus(order.getOrderStatus());
            response.setCreatedAt(order.getCreatedAt());

            // 주문 상세들 가져와서 DTO로 변환
            List<OrderItem> entityItems = orderItemRepository.findByOrderId(order.getOrderId());

            // 💡 내부 클래스 OrderItemResponse 이름에 맞춰 수정
            List<OrderResponseDTO.OrderItemResponse> itemDTOs = entityItems.stream().map(item -> {
                OrderResponseDTO.OrderItemResponse idto = new OrderResponseDTO.OrderItemResponse();

                // 💡 상품 ID로 실제 상품명을 찾아와서 넣어줍니다.
                productRepository.findById(item.getProductId()).ifPresent(product -> {
                    idto.setProductName(product.getName());
                });

                idto.setQuantity(item.getQuantity());
                idto.setPrice(item.getPrice());
                return idto;
            }).collect(Collectors.toList());

            response.setItems(itemDTOs);
            return response;
        }).collect(Collectors.toList());
    }

    /**
     * 3. 주문 상태 변경 (조리완료 등)
     */
    @Transactional
    public void updateOrderStatus(Long orderId, String newStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주문입니다."));
        order.setOrderStatus(newStatus);
    }
}
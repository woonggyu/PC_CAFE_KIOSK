package pc.kiosk.project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pc.kiosk.project.dto.response.ProductResponseDTO;
import pc.kiosk.project.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000") // 💡 리액트 접속 허용
public class ProductController {

    private final ProductService productService;

    /**
     * 전체 상품 목록 조회 (사용자 메뉴판용)
     */
    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        // ProductService에서 DTO 리스트를 가져옵니다.
        List<ProductResponseDTO> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }
}
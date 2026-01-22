package pc.kiosk.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pc.kiosk.project.entity.Product;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // 💡 특정 카테고리에 속한 상품들만 가져오는 마법의 메소드
    List<Product> findByCategoryId(Long categoryId);
}
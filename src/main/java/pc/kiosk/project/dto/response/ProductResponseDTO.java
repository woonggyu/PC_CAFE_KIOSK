package pc.kiosk.project.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductResponseDTO {
    private Long productId;
    private Long categoryId;
    private String categoryName; // 💡 "라면", "음료" 등 카테고리 이름
    private String name;
    private int price;
    private int stock;
    private String imageUrl;
}
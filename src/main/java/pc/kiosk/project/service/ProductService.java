package pc.kiosk.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pc.kiosk.project.dto.response.ProductResponseDTO;
import pc.kiosk.project.entity.Product;
import pc.kiosk.project.repository.CategoryRepository;
import pc.kiosk.project.repository.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    /**
     * 1. 전체 상품 목록 조회 (사용자 메뉴판용)
     * 리액트에서 카테고리 탭을 자동으로 생성하기 위해 categoryName을 포함합니다.
     */
    @Transactional(readOnly = true)
    public List<ProductResponseDTO> getAllProducts() {
        // 모든 상품을 DB에서 가져옴
        List<Product> products = productRepository.findAll();

        return products.stream().map(product -> {
            ProductResponseDTO dto = new ProductResponseDTO();
            dto.setProductId(product.getProductId());
            dto.setName(product.getName());
            dto.setPrice(product.getPrice());
            dto.setImageUrl(product.getImageUrl());
            dto.setStock(product.getStock());
            dto.setCategoryId(product.getCategoryId());

            // 💡 카테고리 ID를 이용해 실제 카테고리 이름(라면, 음료 등)을 찾아 넣습니다.
            categoryRepository.findById(product.getCategoryId()).ifPresent(cat -> {
                dto.setCategoryName(cat.getCategoryName());
            });

            return dto;
        }).collect(Collectors.toList());
    }

    /**
     * 2. 특정 카테고리별 상품 조회 (필요 시 사용)
     */
    @Transactional(readOnly = true)
    public List<ProductResponseDTO> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId).stream().map(product -> {
            ProductResponseDTO dto = new ProductResponseDTO();
            dto.setProductId(product.getProductId());
            dto.setName(product.getName());
            dto.setPrice(product.getPrice());
            dto.setImageUrl(product.getImageUrl());
            dto.setCategoryId(product.getCategoryId());
            return dto;
        }).collect(Collectors.toList());
    }
}
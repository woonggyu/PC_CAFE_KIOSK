package pc.kiosk.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pc.kiosk.project.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    // 기본 CRUD 기능 포함 (save, findById, findAll 등)
}
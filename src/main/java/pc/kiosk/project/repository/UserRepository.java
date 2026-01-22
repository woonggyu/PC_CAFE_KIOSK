package pc.kiosk.project.repository;

// 1. 이 부분을 우리가 만든 엔티티 패키지로 수정해야 합니다.
import pc.kiosk.project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

// JpaRepository<관리할 엔티티, PK 타입>
public interface UserRepository extends JpaRepository<User, Long> {

    // 아이디로 사용자 찾기
    Optional<User> findByUserId(String userId);
}
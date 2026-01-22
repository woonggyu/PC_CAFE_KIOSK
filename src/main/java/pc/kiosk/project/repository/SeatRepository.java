package pc.kiosk.project.repository;

import pc.kiosk.project.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional; // 💡 Optional 추가

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {

    // 1. 좌석 번호(SeatNo)로 특정 좌석 하나만 찾기 (로그인 시 필수!)
    Optional<Seat> findBySeatNo(Long seatNo);

    // 2. 특정 사용자가 앉아있는 좌석 찾기 (이미 사용중인 유저 체크용)
    Optional<Seat> findByUserId(String userId);

    // 3. 기존 기능: 좌석 번호 순으로 전체 보기
    List<Seat> findAllByOrderBySeatNoAsc();
}
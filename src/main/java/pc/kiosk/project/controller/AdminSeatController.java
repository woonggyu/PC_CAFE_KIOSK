package pc.kiosk.project.controller;

import pc.kiosk.project.dto.request.SeatRequestDTO;
import pc.kiosk.project.dto.response.SeatResponseDTO;
import pc.kiosk.project.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/seats")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AdminSeatController {

    private final SeatService seatService;

    @GetMapping
    public ResponseEntity<List<SeatResponseDTO>> getSeats() {
        return ResponseEntity.ok(seatService.getAllSeats());
    }

    @PatchMapping("/{seatNo}")
    public ResponseEntity<SeatResponseDTO> controlSeat(
            @PathVariable Long seatNo,
            @RequestBody SeatRequestDTO requestDTO) {

        SeatResponseDTO updatedSeat = seatService.updateSeat(
                seatNo,
                requestDTO.getStatus(),
                requestDTO.getAddTime(),
                requestDTO.getUserId()
        );

        return ResponseEntity.ok(updatedSeat);
    }
}

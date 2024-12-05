package springboot.cinemaapi.cinemaapifororders.infrastructure.rest.room;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import springboot.cinemaapi.cinemaapifororders.application.dto.room.SeatForSeanceResponse;
import springboot.cinemaapi.cinemaapifororders.application.dto.room.SeatRequest;
import springboot.cinemaapi.cinemaapifororders.application.dto.room.SeatResponse;
import springboot.cinemaapi.cinemaapifororders.application.ports.input.room.SeatService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/room/{roomId}")
public class SeatController {

    private SeatService seatService;

    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('EMPLOYER')")
    @GetMapping("/seats")
    public ResponseEntity<List<SeatResponse>> getSeats(@PathVariable String roomId) {

        return ResponseEntity.ok(seatService.findSeatsByRoomId(roomId));
    }

    @GetMapping("/{repertoireId}/seances/{seanceId}/available-seats")
    public ResponseEntity<List<SeatForSeanceResponse>> getSeatsForSeance(@PathVariable String seanceId, @PathVariable String repertoireId) {
        return ResponseEntity.ok(seatService.findSeatsForSeance(repertoireId,seanceId));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @PutMapping("/seats/{seatId}")
    public ResponseEntity<SeatResponse> updateSeat(@PathVariable String seatId, @Valid @RequestBody SeatRequest seatDto) {
        return ResponseEntity.ok(seatService.updateSeat(seatId, seatDto));
    }

}

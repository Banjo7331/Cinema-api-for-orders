package springboot.cinemaapi.cinemaapifororders.controller.movie;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import springboot.cinemaapi.cinemaapifororders.payload.dto.movie.SeatDto;
import springboot.cinemaapi.cinemaapifororders.service.movie.SeatService;

import java.util.List;

@RestController
@RequestMapping("/api/room/{roomId}")
public class SeatController {

    private SeatService seatService;

    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('EMPLOYER')")
    @GetMapping("/seats")
    public ResponseEntity<List<SeatDto>> getSeats(@PathVariable Long roomId) {

        return ResponseEntity.ok(seatService.findSeatsByRoomId(roomId));
    }

    @GetMapping("/{repertoireId}/seances/{seanceId}/available-seats")
    public ResponseEntity<List<SeatDto>> getSeatsForSeance(@PathVariable Long seanceId, @PathVariable Long repertoireId) {
        return ResponseEntity.ok(seatService.findSeatsForSeance(repertoireId,seanceId));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @PutMapping("/seats/{seatId}")
    public ResponseEntity<SeatDto> updateSeat(@PathVariable Long seatId, @Valid @RequestBody SeatDto seatDto) {
        return ResponseEntity.ok(seatService.updateSeat(seatId, seatDto));
    }

}

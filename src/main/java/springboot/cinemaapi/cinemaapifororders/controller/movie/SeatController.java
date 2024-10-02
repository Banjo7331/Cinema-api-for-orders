package springboot.cinemaapi.cinemaapifororders.controller.movie;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.cinemaapi.cinemaapifororders.entity.reservation.Seat;
import springboot.cinemaapi.cinemaapifororders.payload.dto.movie.SeatDto;
import springboot.cinemaapi.cinemaapifororders.service.movie.SeatService;

import java.util.List;

@RestController
@RequestMapping("/api/rooms/{roomId}")
public class SeatController {

    private SeatService seatService;

    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    @GetMapping("/seats")
    public ResponseEntity<List<SeatDto>> getSeats(@PathVariable Long roomId) {

        return ResponseEntity.ok(seatService.getSeatsByRoomId(roomId));
    }


    @PutMapping("/seats/{seatId}")
    public ResponseEntity<SeatDto> updateSeat(@PathVariable Long seatId, @RequestBody SeatDto seatDto) {
        return ResponseEntity.ok(seatService.updateSeat(seatId, seatDto));
    }

}

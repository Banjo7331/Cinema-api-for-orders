package springboot.cinemaapi.cinemaapifororders.controller.reservation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springboot.cinemaapi.cinemaapifororders.entity.reservation.Reservation;
import springboot.cinemaapi.cinemaapifororders.payload.dto.reservation.ReservationDto;
import springboot.cinemaapi.cinemaapifororders.service.reservation.ReservationService;

import java.util.List;

@RestController
@RequestMapping("/api/reservation")
public class ReservationController {

    private ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<ReservationDto> createReservation(@RequestBody ReservationDto reservationDto) {
        ReservationDto reservation= reservationService.createReservation(reservationDto);

        return new ResponseEntity<>(reservation, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> getReservations(Authentication authentication) {

    }
    @GetMapping("/{id}")
    public ResponseEntity<List<Reservation>> getReservationById(@PathVariable Long id) {

    }

    @DeleteMapping
    public ResponseEntity<Reservation> deleteAllReservation() {

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Reservation> deleteReservation(@PathVariable Long id) {

    }

}

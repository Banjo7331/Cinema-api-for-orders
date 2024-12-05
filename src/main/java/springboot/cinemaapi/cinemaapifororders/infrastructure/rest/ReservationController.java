package springboot.cinemaapi.cinemaapifororders.infrastructure.rest;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;
import springboot.cinemaapi.cinemaapifororders.application.dto.reservation.ReservationRequest;
import springboot.cinemaapi.cinemaapifororders.application.dto.reservation.ReservationResponse;
import springboot.cinemaapi.cinemaapifororders.infrastructure.security.CustomUserDetails;
import springboot.cinemaapi.cinemaapifororders.application.ports.input.repertoire.SeanceService;
import springboot.cinemaapi.cinemaapifororders.application.ports.input.ReservationService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class ReservationController {

    private ReservationService reservationService;

    public ReservationController(ReservationService reservationService, SeanceService seanceService) {
        this.reservationService = reservationService;
    }


    @PostMapping("/reservation")
    public ResponseEntity<ReservationResponse> createReservation(@Valid @RequestBody ReservationRequest reservationDto) {
        ReservationResponse reservation= reservationService.addReservation(reservationDto);

        return new ResponseEntity<>(reservation, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('EMPLOYER')")
    @PutMapping("/reservation/{id}")
    public ResponseEntity<ReservationResponse> updateReservation(@Valid @RequestBody ReservationRequest reservationDto, @PathVariable String id) {
        ReservationResponse updatedReservation= reservationService.updateReservation(reservationDto,id);

        return ResponseEntity.ok(updatedReservation);
    }

    @GetMapping("/reservation/{id}")
    public ResponseEntity<ReservationResponse> getReservationById(@PathVariable String id) {

        return ResponseEntity.ok(reservationService.findReservationById(id));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('EMPLOYER')")
    @GetMapping("/reservation")
    public ResponseEntity<Page<ReservationResponse>> getAllReservations(@RequestParam(required = false) String phoneNumber, @RequestParam(required = false) String email,
                                                                   @RequestParam(defaultValue = "0") Integer page,@RequestParam(defaultValue = "15") Integer size) {

        Page<ReservationResponse> reservations;

        if (phoneNumber != null && !phoneNumber.isEmpty()) {
            reservations = reservationService.findReservationsByPhoneNumber(phoneNumber,page,size);
        } else if(email != null && !email.isEmpty()){
            reservations = reservationService.findReservationsByEmail(email,page,size);
        } else{
            reservations = reservationService.findAllReservations(page,size);
        }

        return ResponseEntity.ok(reservations);

    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('EMPLOYER')")
    @GetMapping("repertoire/{repertoireId}/seances/{seanceId}/reservation")
    public ResponseEntity<Page<ReservationResponse>> getReservationsForSeance(@PathVariable String seanceId, @PathVariable String repertoireId, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "15") Integer size) {
        return ResponseEntity.ok(reservationService.findReservationsBySeanceId(repertoireId,seanceId,page,size));
    }
    @PreAuthorize("hasRole('USER') or hasRole('EMPLOYER') or hasRole('MANAGER') or hasRole('ADMIN')")
    @GetMapping("/reservation/user/{id}")
    public ResponseEntity<Page<ReservationResponse>> getAllUserReservations(@PathVariable String id, Authentication authentication,@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "15") Integer size) {
        Page<ReservationResponse> reservations;

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String userId = userDetails.getId();

        List<GrantedAuthority> authorities = (List<GrantedAuthority>) authentication.getAuthorities();

        if(authorities.size() > 1 || userId.equals(id)) {
            reservations = reservationService.findAllReservationsForUser(id,page,size);
        }else{
            throw new RuntimeException(("No privileges for this endpoint"));
        }

        return ResponseEntity.ok(reservations);
    }

    @DeleteMapping("/reservation/{id}")
    public ResponseEntity<String> deleteReservationsById(@PathVariable String id) {
        reservationService.deleteReservationById(id);

        return ResponseEntity.ok("Deleted All reservations for seance with id: "+id);
    }

    @DeleteMapping("/reservation/user/{id}")
    public ResponseEntity<String> deleteAllUserReservation(@PathVariable String id) {
//        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
//        Long userId = userDetails.getId();

        reservationService.deleteAllUserReservations(id);

        return ResponseEntity.ok("Deleted all your reservations");
    }

    @DeleteMapping("/reservation/seance/{id}")
    public ResponseEntity<String> deleteReservationsForSeance(@PathVariable String id) {
        reservationService.deleteReservationsForSeance(id);

        return ResponseEntity.ok("Deleted All reservations for seance with id: "+id);
    }

    @DeleteMapping("/reservation/movie/{movieName}")
    public ResponseEntity<String> deleteReservationForMovie(@PathVariable String movieName) {

        reservationService.deleteReservationsForMovie(movieName);

        return ResponseEntity.ok("Deleted All reservations for movie "+movieName);
    }

}

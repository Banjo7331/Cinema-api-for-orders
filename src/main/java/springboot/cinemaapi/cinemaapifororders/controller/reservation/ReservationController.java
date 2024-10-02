package springboot.cinemaapi.cinemaapifororders.controller.reservation;

import jakarta.mail.MessagingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springboot.cinemaapi.cinemaapifororders.external.service.EmailService;
import springboot.cinemaapi.cinemaapifororders.payload.dto.reservation.ReservationDto;
import springboot.cinemaapi.cinemaapifororders.security.CustomUserDetails;
import springboot.cinemaapi.cinemaapifororders.service.reservation.ReservationService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/reservation")
public class ReservationController {

    private ReservationService reservationService;

    public ReservationController(ReservationService reservationService/*, EmailService emailService*/) {
        this.reservationService = reservationService;
    }


    @PostMapping
    public ResponseEntity<ReservationDto> createReservation(@RequestBody ReservationDto reservationDto) {
        ReservationDto reservation= reservationService.createReservation(reservationDto);

        return new ResponseEntity<>(reservation, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('EMPLOYER') or hasRole('USER')")
    @PutMapping("/{id}")
    public ResponseEntity<ReservationDto> updateReservation(@RequestBody ReservationDto reservationDto, @PathVariable Long id) {
        ReservationDto updatedReservation= reservationService.updateReservation(reservationDto,id);

        return ResponseEntity.ok(updatedReservation);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationDto> getReservationById(@PathVariable Long id) {

        return ResponseEntity.ok(reservationService.getReservationById(id));
    }

    @GetMapping
    public ResponseEntity<List<ReservationDto>> getAllReservations(@RequestParam(required = false) String phoneNumber, @RequestParam(required = false) String email) {

        List<ReservationDto> reservations;

        if (phoneNumber != null && !phoneNumber.isEmpty()) {
            reservations = reservationService.findReservationsByPhoneNumber(phoneNumber);
        } else if(email != null && !email.isEmpty()){
            reservations = reservationService.findReservationsByEmail(email);
        }

        return ResponseEntity.ok(reservationService.getAllReservations());

    }
    @PreAuthorize("hasRole('USER') or hasRole('EMPLOYER') or hasRole('MANAGER') or hasRole('ADMIN')")
    @GetMapping("/user/{id}")
    public ResponseEntity<List<ReservationDto>> getAllUserReservations(@PathVariable Long id, Authentication authentication) {
        List<ReservationDto> reservations;

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long userId = userDetails.getId();

        List<GrantedAuthority> authorities = (List<GrantedAuthority>) authentication.getAuthorities();

        if(authorities.size() > 1 || userId.equals(id)) {
            reservations = reservationService.getAllReservationsForUser(id);
        }else{
            throw new RuntimeException(("No privileges for this endpoint"));
        }

        return ResponseEntity.ok(reservations);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReservationsById(@PathVariable Long id) {
        reservationService.deleteReservationById(id);

        return ResponseEntity.ok("Deleted All reservations for seance with id: "+id);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<String> deleteAllUserReservation(@PathVariable Long id) {
//        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
//        Long userId = userDetails.getId();

        reservationService.deleteAllUserReservations(id);

        return ResponseEntity.ok("Deleted all your reservations");
    }

    @DeleteMapping("/seance/{id}")
    public ResponseEntity<String> deleteReservationsForSeance(@PathVariable Long id) {
        reservationService.deleteReservationsForSeance(id);

        return ResponseEntity.ok("Deleted All reservations for seance with id: "+id);
    }

    @DeleteMapping("/movie/{movieName}")
    public ResponseEntity<String> deleteReservationForMovie(@PathVariable String movieName) {

        reservationService.deleteReservationsForMovie(movieName);

        return ResponseEntity.ok("Deleted All reservations for movie "+movieName);
    }

}

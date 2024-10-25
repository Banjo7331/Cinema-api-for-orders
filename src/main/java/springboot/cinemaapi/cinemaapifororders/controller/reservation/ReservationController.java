package springboot.cinemaapi.cinemaapifororders.controller.reservation;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;
import springboot.cinemaapi.cinemaapifororders.payload.dto.reservation.ReservationDto;
import springboot.cinemaapi.cinemaapifororders.security.CustomUserDetails;
import springboot.cinemaapi.cinemaapifororders.service.movie.repertoire.SeanceService;
import springboot.cinemaapi.cinemaapifororders.service.reservation.ReservationService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ReservationController {

    private ReservationService reservationService;

    public ReservationController(ReservationService reservationService, SeanceService seanceService) {
        this.reservationService = reservationService;
    }


    @PostMapping("/reservation")
    public ResponseEntity<ReservationDto> createReservation(@Valid @RequestBody ReservationDto reservationDto) {
        ReservationDto reservation= reservationService.addReservation(reservationDto);

        return new ResponseEntity<>(reservation, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('EMPLOYER') or hasRole('USER')")
    @PutMapping("/reservation/{id}")
    public ResponseEntity<ReservationDto> updateReservation(@Valid @RequestBody ReservationDto reservationDto, @PathVariable Long id) {
        ReservationDto updatedReservation= reservationService.updateReservation(reservationDto,id);

        return ResponseEntity.ok(updatedReservation);
    }

    @GetMapping("/reservation/{id}")
    public ResponseEntity<ReservationDto> getReservationById(@PathVariable Long id) {

        return ResponseEntity.ok(reservationService.findReservationById(id));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('EMPLOYER')")
    @GetMapping("/reservation")
    public ResponseEntity<Page<ReservationDto>> getAllReservations(@RequestParam(required = false) String phoneNumber, @RequestParam(required = false) String email,
                                                                   @RequestParam(defaultValue = "0") Integer page,@RequestParam(defaultValue = "15") Integer size) {

        Page<ReservationDto> reservations;

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
    public ResponseEntity<Page<ReservationDto>> getReservationsForSeance(@PathVariable Long seanceId, @PathVariable Long repertoireId, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "15") Integer size) {
        return ResponseEntity.ok(reservationService.findReservationsBySeanceId(repertoireId,seanceId,page,size));
    }
    @PreAuthorize("hasRole('USER') or hasRole('EMPLOYER') or hasRole('MANAGER') or hasRole('ADMIN')")
    @GetMapping("/reservation/user/{id}")
    public ResponseEntity<Page<ReservationDto>> getAllUserReservations(@PathVariable Long id, Authentication authentication,@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "15") Integer size) {
        Page<ReservationDto> reservations;

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long userId = userDetails.getId();

        List<GrantedAuthority> authorities = (List<GrantedAuthority>) authentication.getAuthorities();

        if(authorities.size() > 1 || userId.equals(id)) {
            reservations = reservationService.findAllReservationsForUser(id,page,size);
        }else{
            throw new RuntimeException(("No privileges for this endpoint"));
        }

        return ResponseEntity.ok(reservations);
    }

    @DeleteMapping("/reservation/{id}")
    public ResponseEntity<String> deleteReservationsById(@PathVariable Long id) {
        reservationService.deleteReservationById(id);

        return ResponseEntity.ok("Deleted All reservations for seance with id: "+id);
    }

    @DeleteMapping("/reservation/user/{id}")
    public ResponseEntity<String> deleteAllUserReservation(@PathVariable Long id) {
//        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
//        Long userId = userDetails.getId();

        reservationService.deleteAllUserReservations(id);

        return ResponseEntity.ok("Deleted all your reservations");
    }

    @DeleteMapping("/reservation/seance/{id}")
    public ResponseEntity<String> deleteReservationsForSeance(@PathVariable Long id) {
        reservationService.deleteReservationsForSeance(id);

        return ResponseEntity.ok("Deleted All reservations for seance with id: "+id);
    }

    @DeleteMapping("/reservation/movie/{movieName}")
    public ResponseEntity<String> deleteReservationForMovie(@PathVariable String movieName) {

        reservationService.deleteReservationsForMovie(movieName);

        return ResponseEntity.ok("Deleted All reservations for movie "+movieName);
    }

}

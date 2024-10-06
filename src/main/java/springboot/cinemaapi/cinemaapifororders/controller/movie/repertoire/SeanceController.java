package springboot.cinemaapi.cinemaapifororders.controller.movie.repertoire;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import springboot.cinemaapi.cinemaapifororders.payload.dto.movie.SeatDto;
import springboot.cinemaapi.cinemaapifororders.payload.dto.movie.repertoire.SeanceDto;
import springboot.cinemaapi.cinemaapifororders.service.movie.repertoire.SeanceService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SeanceController {

    private SeanceService seanceService;

    public SeanceController(SeanceService seanceService) {
        this.seanceService = seanceService;
    }

    @GetMapping("/repertoires/{repertoireId}/seances")
    public List<SeanceDto> getSeancesForRepertoire(@PathVariable Long repertoireId) {
        return seanceService.getSeancesForRepertoire(repertoireId);
    }

    @PostMapping("/repertoires/{repertoireId}/seances/{id}")
    public ResponseEntity<SeanceDto> getSeanceById(@PathVariable Long repertoireId, @PathVariable Long id) {
        return ResponseEntity.ok(seanceService.getSeanceById(id, repertoireId));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('EMPLOYER')")
    @GetMapping("/repertoires/{repertoireId}/seances/{seanceId}/reservations")
    public ResponseEntity<List<SeatDto>> getReservationsForSeance(@PathVariable Long seanceId, @PathVariable Long repertoireId) {
        return ResponseEntity.ok(seanceService.getReservedSeatsForSeance(repertoireId,seanceId));
    }

    @GetMapping("/repertoires/{repertoireId}/seances/{seanceId}/available-seats")
    public ResponseEntity<List<SeatDto>> getAvailableSeats(@PathVariable Long seanceId, @PathVariable Long repertoireId) {
        return ResponseEntity.ok(seanceService.getReservedSeatsForSeance(repertoireId,seanceId));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @PostMapping("/repertoires/{repertoireId}/seances")
    public ResponseEntity<SeanceDto> createSeance(@PathVariable Long repertoireId, @Valid @RequestBody SeanceDto seanceDto) {
        return new ResponseEntity<>(seanceService.createSeance(repertoireId,seanceDto), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @PutMapping("/repertoires/{repertoireId}/seances/{seanceId}")
    public ResponseEntity<SeanceDto> updateSeance(@PathVariable Long repertoireId,  @PathVariable Long seanceId, @Valid @RequestBody SeanceDto seanceDto) {
        return new ResponseEntity<>(seanceService.updateSeance(seanceId,repertoireId,seanceDto),HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @DeleteMapping("/repertoires/{repertoireId}/seances/{seanceId}")
    public ResponseEntity<String> deleteSeance(@PathVariable Long repertoireId, @PathVariable Long seanceId) {

        seanceService.deleteSeance(seanceId,repertoireId);

        return ResponseEntity.ok("Seance was successfully deleted");
    }
}

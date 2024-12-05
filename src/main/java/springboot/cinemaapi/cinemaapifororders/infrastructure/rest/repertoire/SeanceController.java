package springboot.cinemaapi.cinemaapifororders.infrastructure.rest.repertoire;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import springboot.cinemaapi.cinemaapifororders.application.dto.repertoire.SeanceRequest;
import springboot.cinemaapi.cinemaapifororders.application.dto.repertoire.SeanceResponse;
import springboot.cinemaapi.cinemaapifororders.application.ports.input.repertoire.SeanceService;

import java.util.UUID;

@RestController
@RequestMapping("/api/repertoire")
public class SeanceController {

    private SeanceService seanceService;

    public SeanceController(SeanceService seanceService) {
        this.seanceService = seanceService;
    }

    @GetMapping("/{repertoireId}/seances")
    public ResponseEntity<Page<SeanceResponse>> getSeancesForRepertoire(@PathVariable String repertoireId, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "15") Integer size) {
        return ResponseEntity.ok(seanceService.findSeancesForRepertoire(repertoireId,page,size));
    }

    @PostMapping("/{repertoireId}/seances/{id}")
    public ResponseEntity<SeanceResponse> getSeanceById(@PathVariable String repertoireId, @PathVariable String id) {
        return ResponseEntity.ok(seanceService.findSeanceById(id, repertoireId));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @PostMapping("/{repertoireId}/seances")
    public ResponseEntity<SeanceResponse> createSeance(@PathVariable String repertoireId, @Valid @RequestBody SeanceRequest seanceDto) {
        return new ResponseEntity<>(seanceService.addSeance(repertoireId,seanceDto), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @PutMapping("/{repertoireId}/seances/{seanceId}")
    public ResponseEntity<SeanceResponse> updateSeance(@PathVariable String repertoireId,  @PathVariable String seanceId, @Valid @RequestBody SeanceRequest seanceDto) {
        return new ResponseEntity<>(seanceService.updateSeance(seanceId,repertoireId,seanceDto),HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @DeleteMapping("/{repertoireId}/seances/{seanceId}")
    public ResponseEntity<String> deleteSeance(@PathVariable String repertoireId, @PathVariable String seanceId) {

        seanceService.deleteSeance(seanceId,repertoireId);

        return ResponseEntity.ok("Seance was successfully deleted");
    }
}

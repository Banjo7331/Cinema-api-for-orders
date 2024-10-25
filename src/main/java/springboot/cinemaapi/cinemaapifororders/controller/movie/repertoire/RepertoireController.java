package springboot.cinemaapi.cinemaapifororders.controller.movie.repertoire;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import springboot.cinemaapi.cinemaapifororders.payload.dto.movie.repertoire.RepertoireDto;
import springboot.cinemaapi.cinemaapifororders.service.movie.repertoire.RepertoireService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/repertoire")
public class RepertoireController {

    private RepertoireService repertoireService;

    public RepertoireController(RepertoireService repertoireService) {
        this.repertoireService = repertoireService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<RepertoireDto> getRepertoireById(@PathVariable Long id) {

        return ResponseEntity.ok(repertoireService.findRepertoireById(id));
    }
    @GetMapping("/date")
    public ResponseEntity<RepertoireDto> getRepertoireByTheDate(@RequestParam LocalDate date) {

        return ResponseEntity.ok(repertoireService.findRepertoireByTheDate(date));
    }

    @GetMapping()
    public ResponseEntity<Page<RepertoireDto>> getRepertoireForNextWeek(@RequestParam(required = false) LocalDate date, @RequestParam(defaultValue = "0") Integer page,
                                                                        @RequestParam(defaultValue = "7") Integer size) {

        return ResponseEntity.ok(repertoireService.findRepertoires(date,page,size));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @PostMapping()
    public ResponseEntity<RepertoireDto> createRepertoire(@Valid  @RequestBody RepertoireDto repertoireDto) {

        return new ResponseEntity<>(repertoireService.addRepertoire(repertoireDto), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @PutMapping("/{id}")
    public ResponseEntity<RepertoireDto> updateRepertoire(@Valid @RequestBody RepertoireDto repertoireDto, @PathVariable Long id) {

        return ResponseEntity.ok(repertoireService.updateRepertoire(id,repertoireDto));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('EMPLOYER')")
    @DeleteMapping()
    public ResponseEntity<String> deleteRepertoriesOlderThanAWeek(@Valid @RequestBody RepertoireDto repertoireDto) {

        repertoireService.deleteRepertoiresOlderThanWeek();
        return ResponseEntity.ok("Week of previous repertoires was deleted");
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRepertoireById(@PathVariable Long id) {

        repertoireService.deleteRepertoireById(id);
        return ResponseEntity.ok("Repertoire was deleted");
    }
}

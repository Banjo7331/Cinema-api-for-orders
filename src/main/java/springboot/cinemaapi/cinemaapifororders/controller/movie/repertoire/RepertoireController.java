package springboot.cinemaapi.cinemaapifororders.controller.movie.repertoire;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.cinemaapi.cinemaapifororders.payload.dto.movie.repertoire.RepertoireDto;
import springboot.cinemaapi.cinemaapifororders.service.movie.repertoire.RepertoireService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/repertoires")
public class RepertoireController {

    private RepertoireService repertoireService;

    public RepertoireController(RepertoireService repertoireService) {
        this.repertoireService = repertoireService;
    }

    @GetMapping()
    public ResponseEntity<RepertoireDto> getRepertoireByTheDate(@RequestParam LocalDate date) {

        return ResponseEntity.ok(repertoireService.getRepertoireByTheDate(date));
    }
    @GetMapping()
    public ResponseEntity<List<RepertoireDto>> getRepertoireForNextWeek(@RequestParam LocalDate date) {

        return ResponseEntity.ok(repertoireService.getFirst7Repertoires(date));
    }

    @PostMapping()
    public ResponseEntity<RepertoireDto> addRepertoire(@RequestBody RepertoireDto repertoireDto) {

        return new ResponseEntity<>(repertoireService.createRepertoire(repertoireDto), HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<RepertoireDto> updateRepertoire(@RequestBody RepertoireDto repertoireDto) {

        return ResponseEntity.ok(repertoireService.updateRepertoire(repertoireDto));
    }

    @DeleteMapping()
    public ResponseEntity<String> deleteRepertoriesOlderThanAWeek(@RequestBody RepertoireDto repertoireDto) {

        repertoireService.deleteRepertoiresOlderThanWeek();
        return ResponseEntity.ok("Week of previous repertoires was deleted");
    }
}

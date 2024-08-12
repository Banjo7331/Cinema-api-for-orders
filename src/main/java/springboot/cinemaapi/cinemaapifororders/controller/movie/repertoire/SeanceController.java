package springboot.cinemaapi.cinemaapifororders.controller.movie.repertoire;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springboot.cinemaapi.cinemaapifororders.entity.reservation.Seance;
import springboot.cinemaapi.cinemaapifororders.service.movie.repertoire.SeanceService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SeanceController {

    private SeanceService seanceService;

    public SeanceController(SeanceService seanceService) {
        this.seanceService = seanceService;
    }

    @GetMapping("/repertoire/{repertoireId}/seances")
    public List<Seance> getSeancesForRepertoire(@PathVariable Long repertoireId) {
        return seanceService.getSeancesForRepertoire(repertoireId)
    }
}

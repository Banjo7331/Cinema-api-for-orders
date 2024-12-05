package springboot.cinemaapi.cinemaapifororders.application.ports.input.repertoire;

import org.springframework.data.domain.Page;
import springboot.cinemaapi.cinemaapifororders.application.dto.repertoire.SeanceRequest;
import springboot.cinemaapi.cinemaapifororders.application.dto.repertoire.SeanceResponse;

import java.util.UUID;

public interface SeanceService {
    Page<SeanceResponse> findSeancesForRepertoire(String repertoireId, Integer page, Integer Size);
    SeanceResponse findSeanceById(String seanceId, String repertoireId);
    SeanceResponse updateSeance(String seanceId, String repertoireId,SeanceRequest seanceDto);
    SeanceResponse addSeance(String repertoireId, SeanceRequest seanceDto);
    void deleteSeance(String seanceId, String repertoireId);
}

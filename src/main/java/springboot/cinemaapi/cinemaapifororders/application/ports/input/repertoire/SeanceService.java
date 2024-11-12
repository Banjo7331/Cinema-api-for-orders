package springboot.cinemaapi.cinemaapifororders.application.ports.input.repertoire;

import org.springframework.data.domain.Page;
import springboot.cinemaapi.cinemaapifororders.application.dto.repertoire.SeanceDto;

public interface SeanceService {
    Page<SeanceDto> findSeancesForRepertoire(Long repertoireId, Integer page, Integer Size);
    SeanceDto findSeanceById(Long seanceId, Long repertoireId);
    SeanceDto updateSeance(Long seanceId, Long repertoireId,SeanceDto seanceDto);
    SeanceDto addSeance(Long repertoireId, SeanceDto seanceDto);
    void deleteSeance(Long seanceId, Long repertoireId);
}

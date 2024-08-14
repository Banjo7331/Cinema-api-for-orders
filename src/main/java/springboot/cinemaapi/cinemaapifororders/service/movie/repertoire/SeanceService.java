package springboot.cinemaapi.cinemaapifororders.service.movie.repertoire;

import springboot.cinemaapi.cinemaapifororders.entity.reservation.Seance;
import springboot.cinemaapi.cinemaapifororders.payload.dto.movie.repertoire.SeanceDto;

import java.util.List;

public interface SeanceService {
    List<SeanceDto> getSeancesForRepertoire(Long repertoireId);
    SeanceDto getSeanceById(Long seanceId, Long repertoireId);
    SeanceDto updateSeance(Long seanceId, Long repertoireId,SeanceDto seanceDto);
    SeanceDto createSeance(Long repertoireId, SeanceDto seanceDto);
    void deleteSeance(Long seanceId, Long repertoireId);
}

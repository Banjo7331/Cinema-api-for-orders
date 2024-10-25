package springboot.cinemaapi.cinemaapifororders.service.movie.repertoire;

import org.springframework.data.domain.Page;
import springboot.cinemaapi.cinemaapifororders.payload.dto.movie.SeatDto;
import springboot.cinemaapi.cinemaapifororders.payload.dto.movie.repertoire.SeanceDto;

import java.util.List;

public interface SeanceService {
    Page<SeanceDto> findSeancesForRepertoire(Long repertoireId, Integer page, Integer Size);
    SeanceDto findSeanceById(Long seanceId, Long repertoireId);
    SeanceDto updateSeance(Long seanceId, Long repertoireId,SeanceDto seanceDto);
    SeanceDto addSeance(Long repertoireId, SeanceDto seanceDto);
    void deleteSeance(Long seanceId, Long repertoireId);
}

package springboot.cinemaapi.cinemaapifororders.service.movie.repertoire;

import springboot.cinemaapi.cinemaapifororders.entity.reservation.Seance;
import springboot.cinemaapi.cinemaapifororders.payload.dto.movie.repertoire.SeanceDto;

import java.util.List;

public interface SeanceService {
    List<SeanceDto> getSeancesForRepertoire(Long repertoireId);
}

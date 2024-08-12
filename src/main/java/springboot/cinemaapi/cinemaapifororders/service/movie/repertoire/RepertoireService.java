package springboot.cinemaapi.cinemaapifororders.service.movie.repertoire;

import springboot.cinemaapi.cinemaapifororders.payload.dto.movie.repertoire.RepertoireDto;

import java.time.LocalDate;
import java.util.List;

public interface RepertoireService {
    RepertoireDto getRepertoireByTheDate(LocalDate date);
    List<RepertoireDto> getFirst7Repertoires(LocalDate date);
    RepertoireDto createRepertoire(RepertoireDto repertoireDto);
    RepertoireDto updateRepertoire(RepertoireDto repertoireDto);
    void deleteRepertoiresOlderThanWeek();
}

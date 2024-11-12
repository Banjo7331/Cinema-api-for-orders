package springboot.cinemaapi.cinemaapifororders.application.ports.input.repertoire;

import org.springframework.data.domain.Page;
import springboot.cinemaapi.cinemaapifororders.application.dto.repertoire.RepertoireDto;

import java.time.LocalDate;

public interface RepertoireService {
    RepertoireDto findRepertoireById(Long repertoireId);
    RepertoireDto findRepertoireByTheDate(LocalDate date);
    Page<RepertoireDto> findRepertoires(LocalDate date,Integer page, Integer size);
    RepertoireDto addRepertoire(RepertoireDto repertoireDto);
    RepertoireDto updateRepertoire(Long repertoireId,RepertoireDto repertoireDto);
    void deleteRepertoireById(Long id);
    void deleteRepertoiresOlderThanWeek();
}

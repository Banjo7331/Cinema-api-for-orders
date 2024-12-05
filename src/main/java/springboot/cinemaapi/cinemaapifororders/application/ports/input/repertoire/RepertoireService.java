package springboot.cinemaapi.cinemaapifororders.application.ports.input.repertoire;

import org.springframework.data.domain.Page;
import springboot.cinemaapi.cinemaapifororders.application.dto.repertoire.RepertoireRequest;
import springboot.cinemaapi.cinemaapifororders.application.dto.repertoire.RepertoireResponse;

import java.time.LocalDate;
import java.util.UUID;

public interface RepertoireService {
    RepertoireResponse findRepertoireById(String repertoireId);
    RepertoireResponse findRepertoireByTheDate(LocalDate date);
    Page<RepertoireResponse> findRepertoires(LocalDate date,Integer page, Integer size);
    RepertoireResponse addRepertoire(RepertoireRequest repertoireDto);
    RepertoireResponse updateRepertoire(String repertoireId,RepertoireRequest repertoireDto);
    void deleteRepertoireById(String id);
    void deleteRepertoiresOlderThanWeek();
}

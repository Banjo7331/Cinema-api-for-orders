package springboot.cinemaapi.cinemaapifororders.application.dto.repertoire;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RepertoireResponse {
    private String id;

    private LocalDate date;

    private List<SeanceResponse> seances;
}

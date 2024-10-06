package springboot.cinemaapi.cinemaapifororders.payload.dto.movie.repertoire;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RepertoireDto {

    private Long id;

    private LocalDate date;

    @Size(min=1,max=30)
    private List<SeanceDto> seancesList;
}

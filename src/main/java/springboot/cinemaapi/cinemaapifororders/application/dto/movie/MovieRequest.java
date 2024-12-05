package springboot.cinemaapi.cinemaapifororders.application.dto.movie;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import springboot.cinemaapi.cinemaapifororders.domain.enums.MovieCategory;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieRequest {

    private Long lengthInMinutes;

    private String name;

    private MovieCategory movieCategory;

    private String description;

    private LocalDate premiereDate;

    private LocalDate endOfPlayingDate;

    private Integer minimumAgeToWatch;
}

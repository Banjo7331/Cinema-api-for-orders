package springboot.cinemaapi.cinemaapifororders.application.dto.movie;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import springboot.cinemaapi.cinemaapifororders.domain.enums.MovieCategory;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieResponse {
    private String id;

    private Long lengthInMinutes;

    private String name;

    private MovieCategory movieCategory;

    private String description;

    private LocalDate premiereDate;

    private LocalDate endOfPlayingDate;

    private Integer minimumAgeToWatch;
}

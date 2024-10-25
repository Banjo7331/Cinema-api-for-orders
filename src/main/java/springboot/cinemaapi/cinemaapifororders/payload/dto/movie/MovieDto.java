package springboot.cinemaapi.cinemaapifororders.payload.dto.movie;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import springboot.cinemaapi.cinemaapifororders.payload.enums.MovieCategory;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieDto {

    private Long id;

    private Long lengthInMinutes;

    private String name;

    private MovieCategory movieCategory;

    private String description;

    private LocalDate premiereDate;

    private LocalDate endOfPlayingDate;

    private Integer minimumAgeToWatch;
}

package springboot.cinemaapi.cinemaapifororders.payload.dto.movie;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieDto {

    private Long id;

    private Long lengthInMinutes;

    private String name;

    private String category;

    private String description;

    private LocalDate premiereDate;

    private LocalDate endOfPlayingDate;

    private Integer minimumAgeToWatch;
}

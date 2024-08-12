package springboot.cinemaapi.cinemaapifororders.payload.dto.movie;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    private Date premiereDate;

    private Date endOfPlayingDate;

    private Integer minimumAgeToWatch;
}

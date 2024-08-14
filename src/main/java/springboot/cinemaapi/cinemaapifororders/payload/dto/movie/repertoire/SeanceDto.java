package springboot.cinemaapi.cinemaapifororders.payload.dto.movie.repertoire;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import springboot.cinemaapi.cinemaapifororders.entity.reservation.Movie;

import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SeanceDto {

    private Long id;

    private Integer takenSeats;

    private LocalTime hourOfStart;

    private Long movieId;

    private Long roomId;

}

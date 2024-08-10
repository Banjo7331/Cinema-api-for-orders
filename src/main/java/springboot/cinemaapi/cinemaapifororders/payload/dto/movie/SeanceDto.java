package springboot.cinemaapi.cinemaapifororders.payload.dto.movie;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import springboot.cinemaapi.cinemaapifororders.entity.reservation.Movie;
import springboot.cinemaapi.cinemaapifororders.entity.reservation.Repertoire;
import springboot.cinemaapi.cinemaapifororders.entity.reservation.Room;

import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SeanceDto {

    private Long id;

    private Integer takenSeats;

    private LocalTime hourOfStart;


    private Movie movieId;

    private Integer roomId;

}

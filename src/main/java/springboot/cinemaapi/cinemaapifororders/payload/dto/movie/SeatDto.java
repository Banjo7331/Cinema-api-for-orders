package springboot.cinemaapi.cinemaapifororders.payload.dto.movie;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import springboot.cinemaapi.cinemaapifororders.entity.reservation.Seat;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SeatDto {

    boolean available;

    private Seat.Type type;

    private boolean broken;
}

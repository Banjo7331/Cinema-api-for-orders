package springboot.cinemaapi.cinemaapifororders.payload.dto.movie;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import springboot.cinemaapi.cinemaapifororders.entity.reservation.Seat;
import springboot.cinemaapi.cinemaapifororders.payload.enums.SeatType;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SeatDto {

    private Long id;

    boolean available;

    private SeatType seatType;

    private Long roomId;

    private boolean broken;
}

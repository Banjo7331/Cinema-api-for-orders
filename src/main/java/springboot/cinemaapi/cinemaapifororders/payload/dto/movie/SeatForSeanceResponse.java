package springboot.cinemaapi.cinemaapifororders.payload.dto.movie;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import springboot.cinemaapi.cinemaapifororders.payload.enums.SeatType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SeatForSeanceResponse {
    private Long id;

    private Long number;

    private SeatType seatType;

    private Long roomId;

    private boolean broken;

    private boolean available;
}

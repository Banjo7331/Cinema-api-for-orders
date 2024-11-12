package springboot.cinemaapi.cinemaapifororders.application.dto.room;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import springboot.cinemaapi.cinemaapifororders.domain.enums.SeatType;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SeatDto {

    private Long number;

    private SeatType seatType;

    private Long roomId;

    private boolean broken;
}

package springboot.cinemaapi.cinemaapifororders.application.dto.room;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import springboot.cinemaapi.cinemaapifororders.domain.enums.SeatType;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SeatRequest {

    private Long number;

    private SeatType seatType;

    private String roomId;

    private boolean broken;

}

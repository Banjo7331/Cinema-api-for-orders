package springboot.cinemaapi.cinemaapifororders.application.dto.reservation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import springboot.cinemaapi.cinemaapifororders.application.dto.room.SeatResponse;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationResponse {
    private String id;

    private String email;

    private String phoneNumber;

    private boolean attendance;

    private String seanceId;

    private String userId;

    private String orderId;

    private List<SeatResponse> seats;
}

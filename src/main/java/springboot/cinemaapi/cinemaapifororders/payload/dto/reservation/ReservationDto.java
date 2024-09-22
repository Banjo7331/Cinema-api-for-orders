package springboot.cinemaapi.cinemaapifororders.payload.dto.reservation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDto {

    private Long id;

    private Integer numberOfViewers;

    private String email;

    private String phoneNumber;

    private Long seanceId;

    private Long userId;

    private Long orderId;
}

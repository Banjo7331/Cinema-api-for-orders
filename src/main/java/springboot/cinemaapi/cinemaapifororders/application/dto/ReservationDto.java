package springboot.cinemaapi.cinemaapifororders.application.dto;

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

    private boolean attendance;

    private Long seanceId;

    private Long userId;

    private Long orderId;
}

package springboot.cinemaapi.cinemaapifororders.application.dto.repertoire;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.UUID;

// ZROBIC UUID ZEBY NIE BYLO PROSTYMI INTEGEREAMI

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SeanceRequest {

    private Integer takenSeats;

    private LocalTime hourOfStart;

    private String movieId;

    private String roomId;
}

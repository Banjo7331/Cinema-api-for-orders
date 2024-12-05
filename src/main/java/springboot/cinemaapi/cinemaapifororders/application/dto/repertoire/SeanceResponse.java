package springboot.cinemaapi.cinemaapifororders.application.dto.repertoire;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SeanceResponse {

    private String id;

    private Integer takenSeats;

    private LocalTime hourOfStart;

    private String movieId;

    private String roomId;
}

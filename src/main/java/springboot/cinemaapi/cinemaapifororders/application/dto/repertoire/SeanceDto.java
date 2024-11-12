package springboot.cinemaapi.cinemaapifororders.application.dto.repertoire;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SeanceDto {

    private Long id;

    private Integer takenSeats;

    private LocalTime hourOfStart;

    private Long movieId;


    private Long roomId;

}

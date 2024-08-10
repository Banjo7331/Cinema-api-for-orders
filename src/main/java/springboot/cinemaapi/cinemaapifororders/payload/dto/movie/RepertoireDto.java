package springboot.cinemaapi.cinemaapifororders.payload.dto.movie;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import springboot.cinemaapi.cinemaapifororders.entity.reservation.Seance;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RepertoireDto {

    private Long id;

    private Date date;

    private List<SeanceDto> reservationList;
}

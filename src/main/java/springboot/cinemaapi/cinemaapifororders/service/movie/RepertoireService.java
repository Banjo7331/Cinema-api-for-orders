package springboot.cinemaapi.cinemaapifororders.service.movie;

import springboot.cinemaapi.cinemaapifororders.payload.dto.movie.RepertoireDto;

import java.util.Date;

public interface RepertoireService {
    RepertoireDto getRepertoireByTheDate(Date myDate);
}

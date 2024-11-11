package springboot.cinemaapi.cinemaapifororders.service.movie;

import springboot.cinemaapi.cinemaapifororders.entity.reservation.Seat;
import springboot.cinemaapi.cinemaapifororders.payload.dto.movie.SeatDto;
import springboot.cinemaapi.cinemaapifororders.payload.dto.movie.SeatForSeanceResponse;

import java.util.List;

public interface SeatService {
    List<SeatDto> findSeatsByRoomId(Long id);
    List<SeatForSeanceResponse> findSeatsForSeance(Long repertoireId, Long seanceId);
    SeatDto updateSeat(Long seatId,SeatDto seatDto);

}

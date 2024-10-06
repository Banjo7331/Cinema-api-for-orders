package springboot.cinemaapi.cinemaapifororders.service.movie;

import springboot.cinemaapi.cinemaapifororders.entity.reservation.Seat;
import springboot.cinemaapi.cinemaapifororders.payload.dto.movie.SeatDto;

import java.util.List;

public interface SeatService {
    List<SeatDto> getSeatsByRoomId(Long id);
    SeatDto updateSeat(Long seatId,SeatDto seatDto);

}

package springboot.cinemaapi.cinemaapifororders.application.ports.input.room;

import springboot.cinemaapi.cinemaapifororders.application.dto.room.SeatDto;
import springboot.cinemaapi.cinemaapifororders.application.dto.room.SeatForSeanceResponse;

import java.util.List;

public interface SeatService {
    List<SeatDto> findSeatsByRoomId(Long id);
    List<SeatForSeanceResponse> findSeatsForSeance(Long repertoireId, Long seanceId);
    SeatDto updateSeat(Long seatId,SeatDto seatDto);

}

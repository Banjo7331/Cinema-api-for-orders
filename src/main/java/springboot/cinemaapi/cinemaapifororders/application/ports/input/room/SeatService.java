package springboot.cinemaapi.cinemaapifororders.application.ports.input.room;

import springboot.cinemaapi.cinemaapifororders.application.dto.room.SeatForSeanceResponse;
import springboot.cinemaapi.cinemaapifororders.application.dto.room.SeatRequest;
import springboot.cinemaapi.cinemaapifororders.application.dto.room.SeatResponse;

import java.util.List;
import java.util.UUID;

public interface SeatService {
    List<SeatResponse> findSeatsByRoomId(String id);
    List<SeatForSeanceResponse> findSeatsForSeance(String repertoireId, String seanceId);
    SeatResponse updateSeat(String seatId, SeatRequest seatDto);

}

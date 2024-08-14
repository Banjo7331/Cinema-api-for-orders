package springboot.cinemaapi.cinemaapifororders.service.movie;

import springboot.cinemaapi.cinemaapifororders.payload.dto.movie.RoomDto;

import java.util.List;

public interface RoomService {
    RoomDto updateRoom(Long roomId,RoomDto roomDto);
    RoomDto createRoom(RoomDto roomDto);
    void deleteRoom(Long roomId);
    List<RoomDto> getAllRooms();
    RoomDto getRoomById(Long roomId);
}

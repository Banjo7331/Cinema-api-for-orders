package springboot.cinemaapi.cinemaapifororders.service.movie;

import springboot.cinemaapi.cinemaapifororders.payload.dto.movie.RoomDto;

import java.util.List;

public interface RoomService {
    RoomDto updateRoom(Long roomId,RoomDto roomDto);
    RoomDto addRoom(RoomDto roomDto);
    void deleteRoom(Long roomId);
    List<RoomDto> findAllRooms();
    RoomDto findRoomById(Long roomId);
}

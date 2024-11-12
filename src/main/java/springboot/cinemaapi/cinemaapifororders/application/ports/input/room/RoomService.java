package springboot.cinemaapi.cinemaapifororders.application.ports.input.room;

import springboot.cinemaapi.cinemaapifororders.application.dto.room.RoomDto;

import java.util.List;

public interface RoomService {
    RoomDto updateRoom(Long roomId,RoomDto roomDto);
    RoomDto addRoom(RoomDto roomDto);
    void deleteRoom(Long roomId);
    List<RoomDto> findAllRooms();
    RoomDto findRoomById(Long roomId);
}

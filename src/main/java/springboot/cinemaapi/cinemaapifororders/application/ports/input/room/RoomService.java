package springboot.cinemaapi.cinemaapifororders.application.ports.input.room;

import springboot.cinemaapi.cinemaapifororders.application.dto.room.RoomRequest;
import springboot.cinemaapi.cinemaapifororders.application.dto.room.RoomResponse;

import java.util.List;
import java.util.UUID;

public interface RoomService {
    RoomResponse updateRoom(String roomId, RoomRequest roomDto);
    RoomResponse addRoom(RoomRequest roomDto);
    void deleteRoom(String roomId);
    List<RoomResponse> findAllRooms();
    RoomResponse findRoomById(String roomId);
}

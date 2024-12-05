package springboot.cinemaapi.cinemaapifororders.application.service.room;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import springboot.cinemaapi.cinemaapifororders.application.dto.room.RoomRequest;
import springboot.cinemaapi.cinemaapifororders.application.dto.room.RoomResponse;
import springboot.cinemaapi.cinemaapifororders.domain.model.room.Room;
import springboot.cinemaapi.cinemaapifororders.infrastructure.persistence.repository.RoomRepository;
import springboot.cinemaapi.cinemaapifororders.application.ports.input.room.RoomService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements RoomService {

    private RoomRepository roomRepository;

    private ModelMapper modelMapper;

    public RoomServiceImpl(RoomRepository roomRepository,ModelMapper modelMapper) {
        this.roomRepository = roomRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<RoomResponse> findAllRooms() {
        return roomRepository.findAll()
                .stream()
                .map(room -> modelMapper.map(room,RoomResponse.class)).collect(Collectors.toList());
    }

    @Override
    public RoomResponse findRoomById(String roomId) {
        Room room = roomRepository.findById(roomId).orElseThrow(()->new RuntimeException("Room not found"));

        return modelMapper.map(room,RoomResponse.class);
    }

    @Override
    public RoomResponse updateRoom(String roomId,RoomRequest roomDto) {
        Room room = roomRepository.findById(roomId).orElseThrow(()->new RuntimeException("Room not found"));

        modelMapper.map(roomDto,room);

        Room updatedRoom = roomRepository.save(room);

        return modelMapper.map(updatedRoom,RoomResponse.class);
    }

    @Override
    public RoomResponse addRoom(RoomRequest roomDto) {
        Room room = modelMapper.map(roomDto,Room.class);

        Room createdRoom = roomRepository.save(room);

        return modelMapper.map(createdRoom,RoomResponse.class);
    }

    @Override
    public void deleteRoom(String roomId) {
        Room room = roomRepository.findById(roomId).orElseThrow(()->new RuntimeException("Room not found"));

        roomRepository.delete(room);
    }


}

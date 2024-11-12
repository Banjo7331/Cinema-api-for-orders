package springboot.cinemaapi.cinemaapifororders.application.service.room;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import springboot.cinemaapi.cinemaapifororders.domain.model.room.Room;
import springboot.cinemaapi.cinemaapifororders.application.dto.room.RoomDto;
import springboot.cinemaapi.cinemaapifororders.infrastructure.persistence.repository.RoomRepository;
import springboot.cinemaapi.cinemaapifororders.application.ports.input.room.RoomService;

import java.util.List;
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
    public List<RoomDto> findAllRooms() {
        return roomRepository.findAll()
                .stream()
                .map(room -> modelMapper.map(room,RoomDto.class)).collect(Collectors.toList());
    }

    @Override
    public RoomDto findRoomById(Long roomId) {
        Room room = roomRepository.findById(roomId).orElseThrow(()->new RuntimeException("Room not found"));

        return modelMapper.map(room,RoomDto.class);
    }

    @Override
    public RoomDto updateRoom(Long roomId,RoomDto roomDto) {
        Room room = roomRepository.findById(roomId).orElseThrow(()->new RuntimeException("Room not found"));

        modelMapper.map(roomDto,room);

        Room updatedRoom = roomRepository.save(room);

        return modelMapper.map(updatedRoom,RoomDto.class);
    }

    @Override
    public RoomDto addRoom(RoomDto roomDto) {
        Room room = modelMapper.map(roomDto,Room.class);

        Room createdRoom = roomRepository.save(room);

        return modelMapper.map(createdRoom,RoomDto.class);
    }

    @Override
    public void deleteRoom(Long roomId) {
        Room room = roomRepository.findById(roomId).orElseThrow(()->new RuntimeException("Room not found"));

        roomRepository.delete(room);
    }


}

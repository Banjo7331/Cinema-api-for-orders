package springboot.cinemaapi.cinemaapifororders.service.movie.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import springboot.cinemaapi.cinemaapifororders.entity.reservation.Room;
import springboot.cinemaapi.cinemaapifororders.payload.dto.movie.RoomDto;
import springboot.cinemaapi.cinemaapifororders.repository.RoomRepository;
import springboot.cinemaapi.cinemaapifororders.service.movie.RoomService;

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
    public List<RoomDto> getAllRooms() {
        return roomRepository.findAll()
                .stream()
                .map(room -> modelMapper.map(room,RoomDto.class)).collect(Collectors.toList());
    }

    @Override
    public RoomDto getRoomById(Long roomId) {
        Room room = roomRepository.findById(roomId).orElseThrow(()->new RuntimeException("Room not found"));

        return modelMapper.map(room,RoomDto.class);
    }

    @Override
    public RoomDto updateRoom(Long roomId,RoomDto roomDto) {
        Room room = roomRepository.findById(roomId).orElseThrow(()->new RuntimeException("Room not found"));

        room.setAvailable(roomDto.isAvailable());
        room.setNumber(roomDto.getNumber());
        room.setSpecial(roomDto.isSpecial());
        room.setNumberOfRows(roomDto.getNumberOfRows());

        Room updatedRoom = roomRepository.save(room);

        return modelMapper.map(updatedRoom,RoomDto.class);
    }

    @Override
    public RoomDto createRoom(RoomDto roomDto) {
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

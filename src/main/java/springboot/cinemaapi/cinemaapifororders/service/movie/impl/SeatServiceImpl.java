package springboot.cinemaapi.cinemaapifororders.service.movie.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import springboot.cinemaapi.cinemaapifororders.entity.reservation.Reservation;
import springboot.cinemaapi.cinemaapifororders.entity.reservation.Room;
import springboot.cinemaapi.cinemaapifororders.entity.reservation.Seance;
import springboot.cinemaapi.cinemaapifororders.entity.reservation.Seat;
import springboot.cinemaapi.cinemaapifororders.payload.dto.movie.SeatDto;
import springboot.cinemaapi.cinemaapifororders.payload.dto.reservation.ReservationDto;
import springboot.cinemaapi.cinemaapifororders.repository.ReservationRepository;
import springboot.cinemaapi.cinemaapifororders.repository.RoomRepository;
import springboot.cinemaapi.cinemaapifororders.repository.SeanceRepository;
import springboot.cinemaapi.cinemaapifororders.repository.SeatRepository;
import springboot.cinemaapi.cinemaapifororders.service.movie.SeatService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SeatServiceImpl implements SeatService {

    private SeatRepository seatRepository;

    private RoomRepository roomRepository;

    private ModelMapper modelMapper;

    public SeatServiceImpl(SeatRepository seatRepository, RoomRepository roomRepository, ModelMapper modelMapper) {
        this.seatRepository = seatRepository;
        this.roomRepository = roomRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<SeatDto> getSeatsByRoomId(Long id) {
        List<Seat> seats = seatRepository.findAllByRoomId(id);

        return seats.stream().map(seat -> modelMapper.map(seat,SeatDto.class)).collect(Collectors.toList());
    }

    @Override
    public SeatDto updateSeat(Long seatId,SeatDto seatDto) {

        Seat seat = seatRepository.findById(seatId).orElseThrow(() -> new RuntimeException("Seat not found with id: "+seatId));

        modelMapper.map(seatDto,seat);

        Long roomId = seatDto.getRoomId();
        if(roomId != null && roomRepository.existsById(roomId)){
            Room room = roomRepository.findById(seatDto.getRoomId()).orElseThrow(() -> new RuntimeException("Room not found with id: "+seatDto.getRoomId()));

            seat.setRoom(room);
        }


        Seat updateSeat = seatRepository.save(seat);

        return modelMapper.map(updateSeat,SeatDto.class);
    }

}

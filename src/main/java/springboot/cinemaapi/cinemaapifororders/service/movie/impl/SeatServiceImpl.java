package springboot.cinemaapi.cinemaapifororders.service.movie.impl;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import springboot.cinemaapi.cinemaapifororders.entity.reservation.*;
import springboot.cinemaapi.cinemaapifororders.payload.dto.movie.SeatDto;
import springboot.cinemaapi.cinemaapifororders.payload.dto.reservation.ReservationDto;
import springboot.cinemaapi.cinemaapifororders.repository.*;
import springboot.cinemaapi.cinemaapifororders.service.movie.SeatService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SeatServiceImpl implements SeatService {

    private ReservationRepository reservationRepository;

    private SeatRepository seatRepository;

    private RoomRepository roomRepository;

    private RepertoireRepository repertoireRepository;

    private SeanceRepository seanceRepository;

    private ModelMapper modelMapper;

    public SeatServiceImpl(SeatRepository seatRepository, RoomRepository roomRepository,RepertoireRepository repertoireRepository, SeanceRepository seanceRepository,ReservationRepository reservationRepository, ModelMapper modelMapper) {
        this.seatRepository = seatRepository;
        this.roomRepository = roomRepository;
        this.repertoireRepository = repertoireRepository;
        this.seanceRepository = seanceRepository;
        this.reservationRepository = reservationRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<SeatDto> findSeatsByRoomId(Long id) {
        List<Seat> seats = seatRepository.findAllByRoomId(id);

        return seats.stream().map(seat -> modelMapper.map(seat,SeatDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<SeatDto> findSeatsForSeance(Long repertoireId,Long seanceId) {
        Repertoire repertoire = repertoireRepository.findById(repertoireId).orElseThrow(()-> new RuntimeException("Repertoire not found"));

        Seance seance = seanceRepository.findById(seanceId).orElseThrow(()-> new RuntimeException("Seance not found"));

        if(!seance.getRepertoire().getId().equals(repertoire.getId())){
            throw new RuntimeException("Seance does not belong to repertoire");
        }

        List<Reservation> reservationList = reservationRepository.findReservationsBySeance(seance);

        return reservationList.stream()
                .flatMap(reservation -> reservation.getSeats().stream())
                .map(seat -> modelMapper.map(seat, SeatDto.class))
                .collect(Collectors.toList());
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

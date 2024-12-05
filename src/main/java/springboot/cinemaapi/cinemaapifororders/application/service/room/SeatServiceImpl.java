package springboot.cinemaapi.cinemaapifororders.application.service.room;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import springboot.cinemaapi.cinemaapifororders.application.dto.room.SeatRequest;
import springboot.cinemaapi.cinemaapifororders.application.dto.room.SeatResponse;
import springboot.cinemaapi.cinemaapifororders.domain.model.Reservation;
import springboot.cinemaapi.cinemaapifororders.domain.model.repertoire.Repertoire;
import springboot.cinemaapi.cinemaapifororders.domain.model.repertoire.Seance;
import springboot.cinemaapi.cinemaapifororders.domain.model.room.Room;
import springboot.cinemaapi.cinemaapifororders.domain.model.room.Seat;
import springboot.cinemaapi.cinemaapifororders.infrastructure.persistence.repository.*;
import springboot.cinemaapi.cinemaapifororders.application.dto.room.SeatForSeanceResponse;
import springboot.cinemaapi.cinemaapifororders.application.ports.input.room.SeatService;

import java.util.List;
import java.util.Set;
import java.util.UUID;
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
    public List<SeatResponse> findSeatsByRoomId(String id) {
        List<Seat> seats = seatRepository.findAllByRoomId(id);

        return seats.stream().map(seat -> modelMapper.map(seat,SeatResponse.class)).collect(Collectors.toList());
    }

    @Override
    public List<SeatForSeanceResponse> findSeatsForSeance(String repertoireId, String seanceId) {

        Repertoire repertoire = repertoireRepository.findById(repertoireId)
                .orElseThrow(() -> new RuntimeException("Repertoire not found"));

        Seance seance = seanceRepository.findById(seanceId)
                .orElseThrow(() -> new RuntimeException("Seance not found"));

        if (!seance.getRepertoire().getId().equals(repertoire.getId())) {
            throw new RuntimeException("Seance does not belong to repertoire");
        }

        List<Reservation> reservationList = reservationRepository.findReservationsBySeance(seance);
        Set<String> reservedSeatIds = reservationList.stream()
                .flatMap(reservation -> reservation.getSeats().stream())
                .map(Seat::getId)
                .collect(Collectors.toSet());

        List<Seat> allSeatsInRoom = seatRepository.findAllByRoomId(seance.getRoom().getId());

        return allSeatsInRoom.stream()
                .map(seat -> {
                    SeatForSeanceResponse response = new SeatForSeanceResponse();
                    response.setId(seat.getId());
                    response.setNumber(seat.getNumber());
                    response.setSeatType(seat.getSeatType());
                    response.setRoomId(seat.getRoom().getId());
                    response.setBroken(seat.getBroken());
                    response.setAvailable(!reservedSeatIds.contains(seat.getId()));
                    return response;
                })
                .collect(Collectors.toList());
    }

    @Override
    public SeatResponse updateSeat(String seatId, SeatRequest seatDto) {

        Seat seat = seatRepository.findById(seatId).orElseThrow(() -> new RuntimeException("Seat not found with id: "+seatId));

        modelMapper.map(seatDto,seat);

        String roomId = seatDto.getRoomId();
        if(roomId != null && roomRepository.existsById(roomId)){
            Room room = roomRepository.findById(seatDto.getRoomId()).orElseThrow(() -> new RuntimeException("Room not found with id: "+seatDto.getRoomId()));

            seat.setRoom(room);
        }


        Seat updateSeat = seatRepository.save(seat);

        return modelMapper.map(updateSeat,SeatResponse.class);
    }

}

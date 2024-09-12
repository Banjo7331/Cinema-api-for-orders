package springboot.cinemaapi.cinemaapifororders.service.reservation.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import springboot.cinemaapi.cinemaapifororders.entity.reservation.Repertoire;
import springboot.cinemaapi.cinemaapifororders.entity.reservation.Reservation;
import springboot.cinemaapi.cinemaapifororders.entity.reservation.Seance;
import springboot.cinemaapi.cinemaapifororders.payload.dto.movie.repertoire.RepertoireDto;
import springboot.cinemaapi.cinemaapifororders.payload.dto.reservation.ReservationDto;
import springboot.cinemaapi.cinemaapifororders.repository.RepertoireRepository;
import springboot.cinemaapi.cinemaapifororders.repository.ReservationRepository;
import springboot.cinemaapi.cinemaapifororders.service.reservation.ReservationService;

import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private ModelMapper modelMapper;

    public ReservationServiceImpl(ModelMapper modelMapper, ReservationRepository reservationRepository, RepertoireRepository repertoireRepository) {
        this.modelMapper = modelMapper;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public ReservationDto createReservation(ReservationDto reservationDto) {
        Reservation reservation = modelMapper.map(reservationDto, Reservation.class);

        Reservation savedReservation = reservationRepository.save(reservation);

        return modelMapper.map(savedReservation,ReservationDto.class);
    }


//    @Override
//    public List<ReservationDto> getAllReservationsForUser() {
//        List<ReservationDto> reservationList = reservationRepository.findAllByUserId()
//        Repertoire repertoire = repertoireRepository.findById(repertoireId).orElseThrow(()-> new RuntimeException("Repertoire not found"));
//
//        return modelMapper.map(repertoire, RepertoireDto.class);
//    }

    @Override
    public ReservationDto getReservationById(Long id) {
        return modelMapper.map(reservationRepository.findById(id).orElseThrow(()-> new RuntimeException("Reservation not found")), ReservationDto.class);
    }

//    @Override
//    public void deleteAllUserReservations(Long seanceId, Long repertoireId) {
//        Repertoire repertoire = repertoireRepository.findById(repertoireId).orElseThrow(()-> new RuntimeException("Repertoire not found"));
//
//        Seance seance = seanceRepository.findById(seanceId).orElseThrow(()-> new RuntimeException("Seance not found"));
//
//        seanceRepository.delete(seance);
//    }
}

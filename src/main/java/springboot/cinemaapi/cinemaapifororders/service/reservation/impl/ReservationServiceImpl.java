package springboot.cinemaapi.cinemaapifororders.service.reservation.impl;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import springboot.cinemaapi.cinemaapifororders.entity.reservation.Movie;
import springboot.cinemaapi.cinemaapifororders.entity.reservation.Repertoire;
import springboot.cinemaapi.cinemaapifororders.entity.reservation.Reservation;
import springboot.cinemaapi.cinemaapifororders.entity.reservation.Seance;
import springboot.cinemaapi.cinemaapifororders.payload.dto.reservation.ReservationDto;
import springboot.cinemaapi.cinemaapifororders.repository.MovieRepository;
import springboot.cinemaapi.cinemaapifororders.repository.RepertoireRepository;
import springboot.cinemaapi.cinemaapifororders.repository.ReservationRepository;
import springboot.cinemaapi.cinemaapifororders.repository.SeanceRepository;
import springboot.cinemaapi.cinemaapifororders.service.reservation.ReservationService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final MovieRepository movieRepository;

    private final SeanceRepository seanceRepository;

    private final ReservationRepository reservationRepository;
    private ModelMapper modelMapper;

    public ReservationServiceImpl(ModelMapper modelMapper, ReservationRepository reservationRepository, RepertoireRepository repertoireRepository, MovieRepository movieRepository, SeanceRepository seanceRepository) {
        this.modelMapper = modelMapper;
        this.reservationRepository = reservationRepository;
        this.movieRepository = movieRepository;
        this.seanceRepository = seanceRepository;
    }

    @Override
    public List<ReservationDto> findReservationsByEmail(String email) {
        return List.of();
    }

    @Override
    public List<ReservationDto> findReservationsByPhoneNumber(String phoneNumber) {
        return List.of();
    }

    @Override
    public List<ReservationDto> findReservationsByUserName(String email) {
        return List.of();
    }

    @Override
    public ReservationDto createReservation(ReservationDto reservationDto) {
        Reservation reservation = modelMapper.map(reservationDto, Reservation.class);

        Reservation savedReservation = reservationRepository.save(reservation);

        return modelMapper.map(savedReservation,ReservationDto.class);
    }


    @Override
    public List<ReservationDto> getAllReservationsForUser(Long id) {
        List<ReservationDto> reservationList = reservationRepository.findAllByUserId(id).stream().map( reservation -> modelMapper.map(reservation, ReservationDto.class))
                .collect(Collectors.toList());

        return reservationList;
    }

    @Override
    public List<ReservationDto> getAllReservations() {
        List<ReservationDto> reservationList = reservationRepository.findAll().stream().map( reservation -> modelMapper.map(reservation, ReservationDto.class))
                .collect(Collectors.toList());

        return reservationList;
    }

    @Override
    public ReservationDto getReservationById(Long id) {
        return modelMapper.map(reservationRepository.findById(id).orElseThrow(()-> new RuntimeException("Reservation not found")), ReservationDto.class);
    }

    @Transactional
    @Override
    public void deleteReservationById(Long id) {
        reservationRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void deleteAllUserReservations(Long userId) {
        reservationRepository.deleteAllByUserId(userId);
    }

    @Transactional
    @Override
    public void deleteReservationsForSeance(Long id) {

        reservationRepository.deleteBySeanceId(id);
    }

    @Transactional
    @Override
    public void deleteReservationsForMovie(String movieName) {


        Movie movie = movieRepository.findByName(movieName);

        if (movie != null) {
            List<Long> seanceIds = seanceRepository.findSeanceIdsByMovieId(movie.getId());

            if (!seanceIds.isEmpty()) {
                reservationRepository.deleteBySeanceIds(seanceIds);
            }
        }else{
            throw new RuntimeException("Movie not found with name: " + movieName);
        }

    }

}

package springboot.cinemaapi.cinemaapifororders.service.reservation.impl;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import springboot.cinemaapi.cinemaapifororders.entity.reservation.Movie;
import springboot.cinemaapi.cinemaapifororders.entity.reservation.Reservation;
import springboot.cinemaapi.cinemaapifororders.entity.reservation.Seance;
import springboot.cinemaapi.cinemaapifororders.external.service.EmailService;
import springboot.cinemaapi.cinemaapifororders.payload.dto.reservation.ReservationDto;
import springboot.cinemaapi.cinemaapifororders.repository.MovieRepository;
import springboot.cinemaapi.cinemaapifororders.repository.ReservationRepository;
import springboot.cinemaapi.cinemaapifororders.repository.SeanceRepository;
import springboot.cinemaapi.cinemaapifororders.service.reservation.ReservationService;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class ReservationServiceImpl implements ReservationService {

    private final MovieRepository movieRepository;

    private final SeanceRepository seanceRepository;

    private final ReservationRepository reservationRepository;

    private ModelMapper modelMapper;

    private EmailService emailService;

    public ReservationServiceImpl(ModelMapper modelMapper, ReservationRepository reservationRepository, MovieRepository movieRepository, SeanceRepository seanceRepository, EmailService emailService) {
        this.modelMapper = modelMapper;
        this.reservationRepository = reservationRepository;
        this.movieRepository = movieRepository;
        this.seanceRepository = seanceRepository;
        this.emailService = emailService;
    }

    @Override
    public List<ReservationDto> findReservationsByEmail(String email) {
        return reservationRepository.findAllByEmail(email).stream().map(reservation -> modelMapper.map(reservation, ReservationDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<ReservationDto> findReservationsByPhoneNumber(String phoneNumber) {
        return reservationRepository.findAllByPhoneNumber(phoneNumber).stream().map(reservation -> modelMapper.map(reservation, ReservationDto.class)).collect(Collectors.toList());
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

    @Override
    public ReservationDto updateReservation(ReservationDto reservationDto, Long id) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(()-> new RuntimeException("Reservation not found"));

        modelMapper.map(reservationDto,reservation);

        return modelMapper.map(reservationRepository.save(reservation),ReservationDto.class);

    }

    @Transactional
    @Override
    public void deleteReservationById(Long id) {

        Reservation reservation = reservationRepository.findById(id).orElseThrow(()-> new RuntimeException("Reservation not found"));

        Date reservationMadeDate = reservation.getDateCreated();

        if(!reservation.getAttendance() && checkDateIfRefundShouldBeDone(reservationMadeDate)) {
            emailService.notifyReservationDeletion(Collections.singletonList(reservation),"Deleted reservation with id:"+reservation.getId(),"Reservation was deleted with id:"+reservation.getId() +
                    " Money from ticket and accessories ordered will be returned in 3 days");
        }

        reservationRepository.deleteById(id);
    }


    @Transactional
    @Override
    public void deleteAllUserReservations(Long userId) {

        List<Reservation> reservationList = reservationRepository.findAllByUserId(userId);

        List<Reservation> reservationListOfLessThanWeekCreated = reservationList.stream().filter(reservation -> {

            Date reservationMadeDate = reservation.getDateCreated();

            return !reservation.getAttendance() && checkDateIfRefundShouldBeDone(reservationMadeDate);

        }).collect(Collectors.toList());

        reservationRepository.deleteAllByUserId(userId);

        emailService.notifyReservationDeletion(reservationListOfLessThanWeekCreated,"Deleted all reservations for User with Id:"+userId,"Reservations was deleted for User with id:"+userId +
                " Money from ticket and accessories ordered will be returned in 3 days");
    }

    @Transactional
    @Override
    public void deleteReservationsForSeance(Long id) {

        Seance seance = seanceRepository.findById(id).orElseThrow(()-> new RuntimeException("Seance not found"));

        String movieName = seance.getMovie().getName();

        reservationRepository.deleteBySeanceId(id);

        emailService.notifyReservationDeletion(seance.getReservations(),"Deleted Seance for movie: " + movieName,"The seance was deleted from the repertoire." +
                " Money from ticked and accessories ordered will be returned in 3 days");

    }

    @Transactional
    @Override
    public void deleteReservationsForMovie(String movieName) {


        Movie movie = movieRepository.findByName(movieName);

        if (movie != null) {

            List<Seance> seances = movie.getSeances();

            List<Long> seanceIds = seanceRepository.findSeanceIdsByMovieId(movie.getId());

            for (Seance seance : seances) {
                emailService.notifyReservationDeletion(seance.getReservations(),"Deleted Seance for movie: " + movieName,"The seance was deleted from the repertoire." +
                        " Money from ticked and accessories ordered will be returned in 3 days");
            }

            if (!seanceIds.isEmpty()) {
                reservationRepository.deleteBySeanceIds(seanceIds);
            }
        }else{
            throw new RuntimeException("Movie not found with name: " + movieName);
        }

    }

    private boolean checkDateIfRefundShouldBeDone(Date reservationMadeDate) {

        Date today = new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(reservationMadeDate);
        calendar.add(Calendar.DAY_OF_YEAR, 7);

        Date sevenDaysLater = calendar.getTime();

        return today.after(sevenDaysLater);
    }

}

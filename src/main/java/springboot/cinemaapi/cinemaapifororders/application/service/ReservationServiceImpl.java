package springboot.cinemaapi.cinemaapifororders.application.service;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import springboot.cinemaapi.cinemaapifororders.domain.model.Movie;
import springboot.cinemaapi.cinemaapifororders.domain.model.repertoire.Repertoire;
import springboot.cinemaapi.cinemaapifororders.domain.model.Reservation;
import springboot.cinemaapi.cinemaapifororders.domain.model.repertoire.Seance;
import springboot.cinemaapi.cinemaapifororders.domain.exception.ReservationLimitExceededException;
import springboot.cinemaapi.cinemaapifororders.application.dto.ReservationDto;
import springboot.cinemaapi.cinemaapifororders.infrastructure.persistence.repository.MovieRepository;
import springboot.cinemaapi.cinemaapifororders.infrastructure.persistence.repository.RepertoireRepository;
import springboot.cinemaapi.cinemaapifororders.infrastructure.persistence.repository.ReservationRepository;
import springboot.cinemaapi.cinemaapifororders.infrastructure.persistence.repository.SeanceRepository;
import springboot.cinemaapi.cinemaapifororders.application.ports.input.ReservationService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class ReservationServiceImpl implements ReservationService {


    @Value("${daily.order.limit}")
    private int dailyOrderLimit;

    private final MovieRepository movieRepository;

    private final SeanceRepository seanceRepository;

    private final RepertoireRepository repertoireRepository;

    private final ReservationRepository reservationRepository;

    private ModelMapper modelMapper;

    private NotifyReservationDeletionUseCase notifyReservationDeletionUseCase;

    public ReservationServiceImpl(ModelMapper modelMapper, ReservationRepository reservationRepository, MovieRepository movieRepository, SeanceRepository seanceRepository, RepertoireRepository repertoireRepository, NotifyReservationDeletionUseCase notifyReservationDeletionUseCase) {
        this.modelMapper = modelMapper;
        this.reservationRepository = reservationRepository;
        this.movieRepository = movieRepository;
        this.seanceRepository = seanceRepository;
        this.notifyReservationDeletionUseCase = notifyReservationDeletionUseCase;

        this.repertoireRepository = repertoireRepository;
    }

    @Override
    public Page<ReservationDto> findReservationsByEmail(String email, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size,Sort.by("dateCreated"));

        return reservationRepository.findAllByEmail(email,pageable).map(reservation -> modelMapper.map(reservation, ReservationDto.class));
    }

    @Override
    public Page<ReservationDto> findReservationsByPhoneNumber(String phoneNumber,Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size,Sort.by("dateCreated"));

        return reservationRepository.findAllByPhoneNumber(phoneNumber,pageable).map(reservation -> modelMapper.map(reservation, ReservationDto.class));
    }


    @Override
    public ReservationDto addReservation(ReservationDto reservationDto) {
        Reservation reservation;
        Reservation savedReservation;

        if (canMakeReservation(reservationDto.getEmail())) {
            reservation = modelMapper.map(reservationDto, Reservation.class);

            savedReservation = reservationRepository.save(reservation);
        } else {
            throw new ReservationLimitExceededException("Osiągnięto dzienny limit zamówień na adres e-mail: " + reservationDto.getEmail());
        }

        return modelMapper.map(savedReservation,ReservationDto.class);
    }


    @Override
    public Page<ReservationDto> findAllReservationsForUser(Long id,Integer page, Integer size) {

        Pageable pageable = PageRequest.of(page, size,Sort.by("dateCreated"));

        Page<ReservationDto> reservationList = reservationRepository.findAllByUserId(id,pageable).map( reservation -> modelMapper.map(reservation, ReservationDto.class));

        return reservationList;
    }

    @Override
    public Page<ReservationDto> findReservationsBySeanceId(Long repertoireId, Long seanceId, Integer page, Integer size) {
        Repertoire repertoire = repertoireRepository.findById(repertoireId).orElseThrow(()-> new RuntimeException("Repertoire not found"));

        Seance seance = seanceRepository.findById(seanceId).orElseThrow(()-> new RuntimeException("Seance not found"));

        if(!seance.getRepertoire().getId().equals(repertoire.getId())){
            throw new RuntimeException("Seance does not belong to repertoire");
        }

        Pageable pageable = PageRequest.of(page, size);

        Page<Reservation> reservationList = reservationRepository.findReservationsBySeance(seance,pageable);

        return reservationList.map(reservation ->modelMapper.map(reservation, ReservationDto.class));
    }

    @Override
    public Page<ReservationDto> findAllReservations(Integer page, Integer size) {

        Pageable pageable = PageRequest.of(page, size,Sort.by("dateCreated"));

        Page<ReservationDto> reservationList = reservationRepository.findAll(pageable).map( reservation -> modelMapper.map(reservation, ReservationDto.class));

        return reservationList;
    }

    @Override
    public ReservationDto findReservationById(Long id) {
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
            notifyReservationDeletionUseCase.notifyReservationDeletion(Collections.singletonList(reservation),"Deleted reservation with id:"+reservation.getId(),"Reservation was deleted with id:"+reservation.getId() +
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

        notifyReservationDeletionUseCase.notifyReservationDeletion(reservationListOfLessThanWeekCreated,"Deleted all reservations for User with Id:"+userId,"Reservations was deleted for User with id:"+userId +
                " Money from ticket and accessories ordered will be returned in 3 days");
    }

    @Transactional
    @Override
    public void deleteReservationsForSeance(Long id) {

        Seance seance = seanceRepository.findById(id).orElseThrow(()-> new RuntimeException("Seance not found"));

        String movieName = seance.getMovie().getName();

        reservationRepository.deleteBySeanceId(id);

        notifyReservationDeletionUseCase.notifyReservationDeletion(seance.getReservations(),"Deleted Seance for movie: " + movieName,"The seance was deleted from the repertoire." +
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
                notifyReservationDeletionUseCase.notifyReservationDeletion(seance.getReservations(),"Deleted Seance for movie: " + movieName,"The seance was deleted from the repertoire." +
                        " Money from ticked and accessories ordered will be returned in 3 days");
            }

            if (!seanceIds.isEmpty()) {
                reservationRepository.deleteBySeanceIds(seanceIds);
            }
        }else{
            throw new RuntimeException("Movie not found with name: " + movieName);
        }

    }

    private boolean canMakeReservation(String email) {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        int orderCount = reservationRepository.countReservationsByEmailAndDate(email, startOfDay);
        return orderCount < dailyOrderLimit;
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

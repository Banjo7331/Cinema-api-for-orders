package springboot.cinemaapi.cinemaapifororders.service.movie.repertoire.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import springboot.cinemaapi.cinemaapifororders.entity.reservation.*;
import springboot.cinemaapi.cinemaapifororders.external.service.EmailService;
import springboot.cinemaapi.cinemaapifororders.payload.dto.movie.SeatDto;
import springboot.cinemaapi.cinemaapifororders.payload.dto.movie.repertoire.SeanceDto;
import springboot.cinemaapi.cinemaapifororders.payload.dto.reservation.ReservationDto;
import springboot.cinemaapi.cinemaapifororders.repository.*;
import springboot.cinemaapi.cinemaapifororders.service.movie.repertoire.SeanceService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SeanceServiceImpl implements SeanceService {

    private SeanceRepository seanceRepository;

    private RepertoireRepository repertoireRepository;

    private MovieRepository movieRepository;

    private ReservationRepository reservationRepository;

    private RoomRepository roomRepository;

    private ModelMapper modelMapper;

    private EmailService emailService;

    public SeanceServiceImpl(SeanceRepository seanceRepository, ModelMapper modelMapper, RepertoireRepository repertoireRepository,ReservationRepository reservationRepository, MovieRepository movieRepository, RoomRepository roomRepository, EmailService emailService) {
        this.seanceRepository = seanceRepository;
        this.repertoireRepository = repertoireRepository;
        this.reservationRepository = reservationRepository;
        this.modelMapper = modelMapper;
        this.movieRepository = movieRepository;
        this.roomRepository = roomRepository;
        this.emailService = emailService;
    }

    @Override
    public List<SeanceDto> getSeancesForRepertoire(Long repertoireId) {
        List<Seance> seances = seanceRepository.findByRepertoireId(repertoireId);

        return seances.stream()
                .map(seance -> modelMapper.map(seance,SeanceDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public SeanceDto getSeanceById(Long seanceId, Long repertoireId) {
        Repertoire repertoire = repertoireRepository.findById(repertoireId).orElseThrow(()-> new RuntimeException("Repertoire not found"));

        Seance seance = seanceRepository.findById(seanceId).orElseThrow(()-> new RuntimeException("Seance not found"));

        if(!seance.getRepertoire().getId().equals(repertoire.getId())){
            throw new RuntimeException("Seance does not belong to repertoire");
        }

        return modelMapper.map(seance,SeanceDto.class);
    }

    @Override
    public List<ReservationDto> findReservationsBySeanceId(Long repertoireId,Long seanceId) {
        Repertoire repertoire = repertoireRepository.findById(repertoireId).orElseThrow(()-> new RuntimeException("Repertoire not found"));

        Seance seance = seanceRepository.findById(seanceId).orElseThrow(()-> new RuntimeException("Seance not found"));

        if(!seance.getRepertoire().getId().equals(repertoire.getId())){
            throw new RuntimeException("Seance does not belong to repertoire");
        }

        List<ReservationDto> reservationList = reservationRepository.findReservationsBySeance(seance).stream().map(reservation -> modelMapper.map(reservation, ReservationDto.class))
                .collect(Collectors.toList());

        return reservationList;
    }

    @Override
    public List<SeatDto> getReservedSeatsForSeance(Long repertoireId,Long seanceId) {
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
    public SeanceDto updateSeance(Long seanceId, Long repertoireId, SeanceDto seanceDto) {
        Repertoire repertoire = repertoireRepository.findById(repertoireId).orElseThrow(()-> new RuntimeException("Repertoire not found"));

        Seance seance = seanceRepository.findById(seanceId).orElseThrow(()-> new RuntimeException("Seance not found"));

        Movie movie = movieRepository.findById(seanceDto.getMovieId()).orElseThrow(()-> new RuntimeException("Movie not found"));

        Room room = roomRepository.findById(seanceDto.getRoomId()).orElseThrow(()-> new RuntimeException("Room not found"));

        if(!seance.getRepertoire().getId().equals(repertoire.getId())){
            throw new RuntimeException("Seance does not belong to repertoire");
        }
        seance.setMovie(movie);
        seance.setTakenSeats(seanceDto.getTakenSeats());
        seance.setHourOfStart(seanceDto.getHourOfStart());
        seance.setRoom(room);


        Seance updatedSeance = seanceRepository.save(seance);

        return modelMapper.map(updatedSeance,SeanceDto.class);
    }

    @Override
    public SeanceDto createSeance(Long repertoireId, SeanceDto seanceDto) {

        Seance seance = modelMapper.map(seanceDto, Seance.class);

        Movie movie = movieRepository.findById(seanceDto.getMovieId()).orElseThrow(()-> new RuntimeException("Movie not found"));

        Room room = roomRepository.findById(seanceDto.getRoomId()).orElseThrow(()-> new RuntimeException("Room not found"));

        Repertoire repertoire = repertoireRepository.findById(repertoireId).orElseThrow(()-> new RuntimeException("Repertoire not found"));

        seance.setMovie(movie);
        seance.setRoom(room);
        seance.setRepertoire(repertoire);

        seanceRepository.save(seance);

        return modelMapper.map(seance, SeanceDto.class);
    }

    @Override
    public void deleteSeance(Long seanceId, Long repertoireId) {
        Repertoire repertoire = repertoireRepository.findById(repertoireId).orElseThrow(()-> new RuntimeException("Repertoire not found"));

        Seance seance = seanceRepository.findById(seanceId).orElseThrow(()-> new RuntimeException("Seance not found"));

        if (!seance.getRepertoire().getId().equals(repertoireId)) {
            throw new RuntimeException("Seance does not belong to the provided Repertoire");
        }

        String movieName = seance.getMovie().getName();

        seanceRepository.delete(seance);

        emailService.notifyReservationDeletion(seance.getReservations(),"Deleted reservation for Seance of Movie: "+movieName,"The reservations were deleted from database." +
                " Money from ticked and accessories ordered will be returned in 3 days");

    }
}

package springboot.cinemaapi.cinemaapifororders.service.movie.repertoire.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import springboot.cinemaapi.cinemaapifororders.entity.reservation.*;
import springboot.cinemaapi.cinemaapifororders.external.service.EmailService;
import springboot.cinemaapi.cinemaapifororders.payload.dto.movie.repertoire.SeanceDto;
import springboot.cinemaapi.cinemaapifororders.repository.MovieRepository;
import springboot.cinemaapi.cinemaapifororders.repository.RepertoireRepository;
import springboot.cinemaapi.cinemaapifororders.repository.RoomRepository;
import springboot.cinemaapi.cinemaapifororders.repository.SeanceRepository;
import springboot.cinemaapi.cinemaapifororders.service.movie.repertoire.SeanceService;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SeanceServiceImpl implements SeanceService {

    private SeanceRepository seanceRepository;

    private RepertoireRepository repertoireRepository;

    private MovieRepository movieRepository;

    private RoomRepository roomRepository;

    private ModelMapper modelMapper;

    private EmailService emailService;

    public SeanceServiceImpl(SeanceRepository seanceRepository, ModelMapper modelMapper, RepertoireRepository repertoireRepository, MovieRepository movieRepository, RoomRepository roomRepository, EmailService emailService) {
        this.seanceRepository = seanceRepository;
        this.repertoireRepository = repertoireRepository;
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

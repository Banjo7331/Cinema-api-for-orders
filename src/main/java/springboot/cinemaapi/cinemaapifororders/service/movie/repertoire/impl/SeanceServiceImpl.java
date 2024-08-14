package springboot.cinemaapi.cinemaapifororders.service.movie.repertoire.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import springboot.cinemaapi.cinemaapifororders.entity.reservation.Movie;
import springboot.cinemaapi.cinemaapifororders.entity.reservation.Repertoire;
import springboot.cinemaapi.cinemaapifororders.entity.reservation.Room;
import springboot.cinemaapi.cinemaapifororders.entity.reservation.Seance;
import springboot.cinemaapi.cinemaapifororders.payload.dto.movie.repertoire.SeanceDto;
import springboot.cinemaapi.cinemaapifororders.repository.MovieRepository;
import springboot.cinemaapi.cinemaapifororders.repository.RepertoireRepository;
import springboot.cinemaapi.cinemaapifororders.repository.RoomRepository;
import springboot.cinemaapi.cinemaapifororders.repository.SeanceRepository;
import springboot.cinemaapi.cinemaapifororders.service.movie.repertoire.SeanceService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SeanceServiceImpl implements SeanceService {

    private SeanceRepository seanceRepository;

    private RepertoireRepository repertoireRepository;

    private MovieRepository movieRepository;

    private RoomRepository roomRepository;

    private ModelMapper modelMapper;

    public SeanceServiceImpl(SeanceRepository seanceRepository, ModelMapper modelMapper, RepertoireRepository repertoireRepository, MovieRepository movieRepository, RoomRepository roomRepository) {
        this.seanceRepository = seanceRepository;
        this.repertoireRepository = repertoireRepository;
        this.modelMapper = modelMapper;
        this.movieRepository = movieRepository;
        this.roomRepository = roomRepository;
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

        seanceRepository.delete(seance);
    }
}

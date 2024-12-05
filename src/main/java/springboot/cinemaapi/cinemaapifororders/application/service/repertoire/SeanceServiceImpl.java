package springboot.cinemaapi.cinemaapifororders.application.service.repertoire;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import springboot.cinemaapi.cinemaapifororders.application.dto.repertoire.SeanceRequest;
import springboot.cinemaapi.cinemaapifororders.application.dto.repertoire.SeanceResponse;
import springboot.cinemaapi.cinemaapifororders.application.service.NotifyReservationDeletionUseCase;
import springboot.cinemaapi.cinemaapifororders.domain.model.Movie;
import springboot.cinemaapi.cinemaapifororders.domain.model.repertoire.Repertoire;
import springboot.cinemaapi.cinemaapifororders.domain.model.repertoire.Seance;
import springboot.cinemaapi.cinemaapifororders.domain.model.room.Room;
import springboot.cinemaapi.cinemaapifororders.infrastructure.persistence.repository.*;
import springboot.cinemaapi.cinemaapifororders.application.ports.input.repertoire.SeanceService;

import java.util.UUID;

@Service
public class SeanceServiceImpl implements SeanceService {

    private SeanceRepository seanceRepository;

    private RepertoireRepository repertoireRepository;

    private MovieRepository movieRepository;

    private RoomRepository roomRepository;

    private ModelMapper modelMapper;

    private NotifyReservationDeletionUseCase notifyReservationDeletionUseCase;

    public SeanceServiceImpl(SeanceRepository seanceRepository, ModelMapper modelMapper, RepertoireRepository repertoireRepository, ReservationRepository reservationRepository, MovieRepository movieRepository, RoomRepository roomRepository, NotifyReservationDeletionUseCase notifyReservationDeletionUseCase) {
        this.seanceRepository = seanceRepository;
        this.repertoireRepository = repertoireRepository;
        this.modelMapper = modelMapper;
        this.movieRepository = movieRepository;
        this.roomRepository = roomRepository;
        this.notifyReservationDeletionUseCase = notifyReservationDeletionUseCase;
    }

    @Override
    public Page<SeanceResponse> findSeancesForRepertoire(String repertoireId, Integer page, Integer size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Seance> seances = seanceRepository.findByRepertoireId(repertoireId,pageable);

        return seances.map(seance ->modelMapper.map(seance, SeanceResponse.class));
    }

    @Override
    public SeanceResponse findSeanceById(String seanceId, String repertoireId) {
        Repertoire repertoire = repertoireRepository.findById(repertoireId).orElseThrow(()-> new RuntimeException("Repertoire not found"));

        Seance seance = seanceRepository.findById(seanceId).orElseThrow(()-> new RuntimeException("Seance not found"));

        if(!seance.getRepertoire().getId().equals(repertoire.getId())){
            throw new RuntimeException("Seance does not belong to repertoire");
        }

        return modelMapper.map(seance,SeanceResponse.class);
    }

    @Override
    public SeanceResponse updateSeance(String seanceId, String repertoireId, SeanceRequest seanceDto) {
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

        return modelMapper.map(updatedSeance,SeanceResponse.class);
    }

    @Override
    public SeanceResponse addSeance(String repertoireId, SeanceRequest seanceDto) {

        Seance seance = modelMapper.map(seanceDto, Seance.class);

        Movie movie = movieRepository.findById(seanceDto.getMovieId()).orElseThrow(()-> new RuntimeException("Movie not found"));

        Room room = roomRepository.findById(seanceDto.getRoomId()).orElseThrow(()-> new RuntimeException("Room not found"));

        Repertoire repertoire = repertoireRepository.findById(repertoireId).orElseThrow(()-> new RuntimeException("Repertoire not found"));

        seance.setMovie(movie);
        seance.setRoom(room);
        seance.setRepertoire(repertoire);

        seanceRepository.save(seance);

        return modelMapper.map(seance, SeanceResponse.class);
    }

    @Override
    public void deleteSeance(String seanceId, String repertoireId) {
        Repertoire repertoire = repertoireRepository.findById(repertoireId).orElseThrow(()-> new RuntimeException("Repertoire not found"));

        Seance seance = seanceRepository.findById(seanceId).orElseThrow(()-> new RuntimeException("Seance not found"));

        if (!seance.getRepertoire().getId().equals(repertoireId)) {
            throw new RuntimeException("Seance does not belong to the provided Repertoire");
        }

        String movieName = seance.getMovie().getName();

        seanceRepository.delete(seance);

        notifyReservationDeletionUseCase.notifyReservationDeletion(seance.getReservations(),"Deleted reservation for Seance of Movie: "+movieName,"The reservations were deleted from database." +
                " Money from ticked and accessories ordered will be returned in 3 days");

    }
}

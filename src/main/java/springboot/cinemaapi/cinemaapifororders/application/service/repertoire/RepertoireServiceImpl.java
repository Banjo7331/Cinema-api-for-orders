package springboot.cinemaapi.cinemaapifororders.application.service.repertoire;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springboot.cinemaapi.cinemaapifororders.application.dto.repertoire.RepertoireRequest;
import springboot.cinemaapi.cinemaapifororders.application.dto.repertoire.RepertoireResponse;
import springboot.cinemaapi.cinemaapifororders.application.service.NotifyReservationDeletionUseCase;
import springboot.cinemaapi.cinemaapifororders.domain.model.repertoire.Repertoire;
import springboot.cinemaapi.cinemaapifororders.domain.model.repertoire.Seance;
import springboot.cinemaapi.cinemaapifororders.infrastructure.persistence.repository.RepertoireRepository;
import springboot.cinemaapi.cinemaapifororders.application.ports.input.repertoire.RepertoireService;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class RepertoireServiceImpl implements RepertoireService {

    private RepertoireRepository repertoireRepository;

    private ModelMapper modelMapper;

    private NotifyReservationDeletionUseCase notifyReservationDeletionUseCase;


    public RepertoireServiceImpl(RepertoireRepository repertoireRepository, ModelMapper modelMapper, NotifyReservationDeletionUseCase notifyReservationDeletionUseCase) {
        this.repertoireRepository = repertoireRepository;
        this.modelMapper = modelMapper;
        this.notifyReservationDeletionUseCase = notifyReservationDeletionUseCase;
    }

    @Override
    public RepertoireResponse findRepertoireById(String repertoireId) {
        Repertoire repertoire = repertoireRepository.findById(repertoireId).orElseThrow(()-> new RuntimeException("Repertoire not found"));

        return modelMapper.map(repertoire, RepertoireResponse.class);
    }

    @Override
    public RepertoireResponse findRepertoireByTheDate(LocalDate date) {
        Repertoire repertoire = repertoireRepository.findByDate(date);

        return modelMapper.map(repertoire, RepertoireResponse.class);

    }

    @Override
    public Page<RepertoireResponse> findRepertoires(LocalDate date,Integer page, Integer size) {
        if (date == null) {
            date = LocalDate.now();
        }

        LocalDate startDate = date.minusDays(3);
        LocalDate endDate = date.plusDays(3);

        Pageable pageable = PageRequest.of(page, size);

        Page<Repertoire> repertoirePage = repertoireRepository.findAllBetweenDates(startDate, endDate, pageable);

        return repertoirePage.map(repertoire -> modelMapper.map(repertoire, RepertoireResponse.class));
    }

    @Override
    public RepertoireResponse addRepertoire(RepertoireRequest repertoireDto) {
        Repertoire repertoire = modelMapper.map(repertoireDto, Repertoire.class);

        Repertoire savedRepertoire = repertoireRepository.save(repertoire);

        return modelMapper.map(savedRepertoire, RepertoireResponse.class);
    }

    @Override
    public RepertoireResponse updateRepertoire(String repertoireId, RepertoireRequest repertoireDto) {

        Repertoire repertoire = repertoireRepository.findById(repertoireId).orElseThrow(()-> new RuntimeException("Repertoire not found"));

        repertoire.setDate(repertoireDto.getDate());


        repertoireRepository.save(repertoire);

        return modelMapper.map(repertoire, RepertoireResponse.class);
    }

    @Override
    public void deleteRepertoireById(String id) {
        Repertoire repertoire = repertoireRepository.findById(id).orElseThrow(()-> new RuntimeException("Repertoire not found"));

        List<Seance> seances = repertoire.getSeancesList();

        repertoireRepository.delete(repertoire);

        for (Seance seance : seances) {
            notifyReservationDeletionUseCase.notifyReservationDeletion(seance.getReservations(),"Deleted Seance for repertoire of the day: " + repertoire.getDate(),"The seance were deleted from the repertoire." +
                    " Money from tickets and accessories ordered will be returned in 3 days");
        }

    }


    @Transactional
    @Override
    public void deleteRepertoiresOlderThanWeek() {
        LocalDate currentDate = LocalDate.now();

        LocalDate deleteToDate = currentDate.minusDays(7);

        repertoireRepository.deleteAllWithDateBefore(deleteToDate);
    }
}

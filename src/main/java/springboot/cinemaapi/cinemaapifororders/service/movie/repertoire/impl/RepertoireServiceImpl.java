package springboot.cinemaapi.cinemaapifororders.service.movie.repertoire.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import springboot.cinemaapi.cinemaapifororders.entity.reservation.Movie;
import springboot.cinemaapi.cinemaapifororders.entity.reservation.Repertoire;
import springboot.cinemaapi.cinemaapifororders.payload.dto.movie.repertoire.RepertoireDto;
import springboot.cinemaapi.cinemaapifororders.repository.RepertoireRepository;
import springboot.cinemaapi.cinemaapifororders.service.movie.repertoire.RepertoireService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RepertoireServiceImpl implements RepertoireService {

    private RepertoireRepository repertoireRepository;

    private ModelMapper modelMapper;

    public RepertoireServiceImpl(RepertoireRepository repertoireRepository, ModelMapper modelMapper) {
        this.repertoireRepository = repertoireRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public RepertoireDto getRepertoireById(Long repertoireId) {
        Repertoire repertoire = repertoireRepository.findById(repertoireId).orElseThrow(()-> new RuntimeException("Repertoire not found"));

        return modelMapper.map(repertoire, RepertoireDto.class);
    }

    @Override
    public RepertoireDto getRepertoireByTheDate(LocalDate date) {
        Repertoire repertoire = repertoireRepository.findByDate(date);

        return modelMapper.map(repertoire, RepertoireDto.class);

    }

    @Override
    public List<RepertoireDto> getFirst7Repertoires(LocalDate date) {
        LocalDate startDate = date.minusDays(3);
        LocalDate endDate = date.plusDays(3);

        List<Repertoire> repertoires = repertoireRepository.findAllBetweenDates(startDate, endDate);

        return repertoires.stream()
                .map(repertoire ->modelMapper.map(repertoire, RepertoireDto.class))
                .collect(Collectors.toList());

    }

    @Override
    public RepertoireDto createRepertoire(RepertoireDto repertoireDto) {
        Repertoire repertoire = modelMapper.map(repertoireDto, Repertoire.class);

        Repertoire savedRepertoire = repertoireRepository.save(repertoire);

        return modelMapper.map(savedRepertoire, RepertoireDto.class);
    }

    @Override
    public RepertoireDto updateRepertoire(Long repertoireId,RepertoireDto repertoireDto) {

        Repertoire repertoire = repertoireRepository.findById(repertoireId).orElseThrow(()-> new RuntimeException("Repertoire not found"));

        repertoire.setDate(repertoireDto.getDate());


        repertoireRepository.save(repertoire);

        return modelMapper.map(repertoire, RepertoireDto.class);
    }

    @Override
    public void deleteRepertoireById(Long id) {
        Repertoire repertoire = repertoireRepository.findById(id).orElseThrow(()-> new RuntimeException("Repertoire not found"));

        repertoireRepository.delete(repertoire);
    }

    @Override
    public void deleteRepertoiresOlderThanWeek() {
        LocalDate currentDate = LocalDate.now();

        LocalDate deleteToDate = currentDate.minusDays(7);

        repertoireRepository.deleteAllWithDateBefore(deleteToDate);
    }
}

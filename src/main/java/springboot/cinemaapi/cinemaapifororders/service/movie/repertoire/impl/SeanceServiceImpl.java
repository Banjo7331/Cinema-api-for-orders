package springboot.cinemaapi.cinemaapifororders.service.movie.repertoire.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import springboot.cinemaapi.cinemaapifororders.entity.reservation.Seance;
import springboot.cinemaapi.cinemaapifororders.payload.dto.movie.repertoire.SeanceDto;
import springboot.cinemaapi.cinemaapifororders.repository.SeanceRepository;
import springboot.cinemaapi.cinemaapifororders.service.movie.repertoire.SeanceService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SeanceServiceImpl implements SeanceService {

    private SeanceRepository seanceRepository;

    private ModelMapper modelMapper;

    public SeanceServiceImpl(SeanceRepository seanceRepository, ModelMapper modelMapper) {
        this.seanceRepository = seanceRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<SeanceDto> getSeancesForRepertoire(Long repertoireId) {
        List<Seance> seances = seanceRepository.findByRepertoireId(repertoireId);

        return seances.stream()
                .map(seance -> modelMapper.map(seance,SeanceDto.class))
                .collect(Collectors.toList());
    }
}

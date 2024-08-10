package springboot.cinemaapi.cinemaapifororders.service.movie.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import springboot.cinemaapi.cinemaapifororders.entity.reservation.Repertoire;
import springboot.cinemaapi.cinemaapifororders.payload.dto.movie.RepertoireDto;
import springboot.cinemaapi.cinemaapifororders.repository.RepertoireRepository;
import springboot.cinemaapi.cinemaapifororders.service.movie.RepertoireService;

import java.util.Date;

@Service
public class RepertoireServiceImpl implements RepertoireService {

    private RepertoireRepository repertoireRepository;

    private ModelMapper modelMapper;

    public RepertoireServiceImpl(RepertoireRepository repertoireRepository, ModelMapper modelMapper) {
        this.repertoireRepository = repertoireRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public RepertoireDto getRepertoireByTheDate(Date myDate) {
        Repertoire repertoire = repertoireRepository.findByDate(myDate);

        return modelMapper.map(repertoire, RepertoireDto.class);

    }
}

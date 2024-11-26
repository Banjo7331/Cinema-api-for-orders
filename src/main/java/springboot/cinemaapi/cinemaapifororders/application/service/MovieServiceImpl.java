package springboot.cinemaapi.cinemaapifororders.application.service;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springboot.cinemaapi.cinemaapifororders.domain.model.Movie;
import springboot.cinemaapi.cinemaapifororders.domain.model.Reservation;
import springboot.cinemaapi.cinemaapifororders.domain.model.repertoire.Seance;
import springboot.cinemaapi.cinemaapifororders.application.dto.MovieDto;
import springboot.cinemaapi.cinemaapifororders.domain.enums.MovieCategory;
import springboot.cinemaapi.cinemaapifororders.infrastructure.persistence.repository.MovieRepository;
import springboot.cinemaapi.cinemaapifororders.application.ports.input.MovieService;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Pageable;
@Service
public class MovieServiceImpl implements MovieService {

    private MovieRepository movieRepository;

    private ModelMapper modelMapper;

    private NotifyReservationDeletionUseCase notifyReservationDeletionUseCase;

    public MovieServiceImpl(MovieRepository movieRepository, ModelMapper modelMapper, NotifyReservationDeletionUseCase notifyReservationDeletionUseCase) {
        this.movieRepository = movieRepository;
        this.modelMapper = modelMapper;
        this.notifyReservationDeletionUseCase = notifyReservationDeletionUseCase;
    }

    @Override
    public Page<MovieDto> findAllMovies(Integer page, Integer size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<MovieDto> movies = movieRepository.findAll(pageable).map(movie -> modelMapper.map(movie, MovieDto.class));

        return  movies;
    }

    @Override
    public MovieDto findMovieById(Long movieId) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new RuntimeException("Movie not found"));

        return modelMapper.map(movie, MovieDto.class);
    }

    @Override
    public List<MovieDto> findMoviesByMovieCategory(MovieCategory movieCategory) {
        List<MovieDto> movies = movieRepository.findMoviesByMovieCategory(movieCategory).stream().map(movie -> modelMapper.map(movie, MovieDto.class))
                .collect(Collectors.toList());

        return  movies;

    }

    @Override
    public MovieDto addMovie(MovieDto movieDto) {
        Movie movie = modelMapper.map(movieDto, Movie.class);

        Movie cretedMovie = movieRepository.save(movie);

        return modelMapper.map(cretedMovie, MovieDto.class);
    }

    @Override
    public MovieDto updateMovie(Long movieId, MovieDto movieDto) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(()-> new RuntimeException("Movie not found"));

        modelMapper.map(movieDto, movie);

        Movie updatedMovie = movieRepository.save(movie);

        return modelMapper.map(updatedMovie, MovieDto.class);
    }

    @Override
    public void deleteMovie(Long movieId) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(()-> new RuntimeException("Movie not found"));

        List<Seance> seances = movie.getSeances();

        for(Seance seance : seances){
            List<Reservation> reservationListOfLessThanWeekCreated = seance.getReservations().stream().filter(reservation -> {

                Date reservationMadeDate = reservation.getDateCreated();

                return !reservation.getAttendance() && checkDateIfRefundShouldBeDone(reservationMadeDate);

            }).collect(Collectors.toList());

            notifyReservationDeletionUseCase.notifyReservationDeletion( reservationListOfLessThanWeekCreated,"Deleted Movie from Repertoires: " + movie.getName(),"All seances for this movie were deleted." +
                    " Money from ticked and accessories ordered will be returned in 3 days");
        }

        movieRepository.delete(movie);
    }

    @Override
    public void deleteAllMovies() {
        movieRepository.deleteAll();
    }


    @Transactional
    @Override
    public void deleteExpiredMovies() {
        LocalDate today = LocalDate.now();
        movieRepository.deleteMoviesByEndOfPlayingDateBefore(today);
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

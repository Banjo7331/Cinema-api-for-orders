package springboot.cinemaapi.cinemaapifororders.service.movie.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springboot.cinemaapi.cinemaapifororders.entity.reservation.Movie;
import springboot.cinemaapi.cinemaapifororders.entity.reservation.Reservation;
import springboot.cinemaapi.cinemaapifororders.entity.reservation.Seance;
import springboot.cinemaapi.cinemaapifororders.external.service.EmailService;
import springboot.cinemaapi.cinemaapifororders.payload.dto.movie.MovieDto;
import springboot.cinemaapi.cinemaapifororders.payload.enums.MovieCategory;
import springboot.cinemaapi.cinemaapifororders.repository.MovieRepository;
import springboot.cinemaapi.cinemaapifororders.service.movie.MovieService;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {

    private MovieRepository movieRepository;

    private ModelMapper modelMapper;

    private EmailService emailService;

    public MovieServiceImpl(MovieRepository movieRepository, ModelMapper modelMapper, EmailService emailService) {
        this.movieRepository = movieRepository;
        this.modelMapper = modelMapper;
        this.emailService = emailService;
    }

    @Override
    public List<MovieDto> getAllMovies() {
        List<MovieDto> movies = movieRepository.findAll().stream().map(movie -> modelMapper.map(movie, MovieDto.class))
                .collect(Collectors.toList());

        return  movies;
    }

    @Override
    public MovieDto getMovieById(Long movieId) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new RuntimeException("Movie not found"));

        return modelMapper.map(movie, MovieDto.class);
    }

    @Override
    public List<MovieDto> getMoviesByMovieCategory(MovieCategory movieCategory) {
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

            emailService.notifyReservationDeletion( reservationListOfLessThanWeekCreated,"Deleted Movie from Repertoires: " + movie.getName(),"All seances for this movie were deleted." +
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

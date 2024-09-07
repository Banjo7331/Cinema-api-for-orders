package springboot.cinemaapi.cinemaapifororders.service.movie.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import springboot.cinemaapi.cinemaapifororders.entity.reservation.Movie;
import springboot.cinemaapi.cinemaapifororders.payload.dto.movie.MovieDto;
import springboot.cinemaapi.cinemaapifororders.repository.MovieRepository;
import springboot.cinemaapi.cinemaapifororders.service.movie.MovieService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {

    private MovieRepository movieRepository;

    private ModelMapper modelMapper;

    public MovieServiceImpl(MovieRepository movieRepository, ModelMapper modelMapper) {
        this.movieRepository = movieRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<MovieDto> getAllMovies() {
        List<Movie> movies = movieRepository.findAll();

        System.out.println(movies);

        List<MovieDto> moviesDto = movies.stream().map(movie -> modelMapper.map(movie, MovieDto.class)).collect(Collectors.toList());

        return  moviesDto;
    }

    @Override
    public MovieDto getMovieById(Long movieId) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new RuntimeException("Movie not found"));

        return modelMapper.map(movie, MovieDto.class);
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

        movie.setName(movieDto.getName());
        movie.setLengthInMinutes(movieDto.getLengthInMinutes());
        movie.setCategory(movieDto.getCategory());
        movie.setDescription(movieDto.getDescription());
        movie.setPremiereDate(movieDto.getPremiereDate());
        movie.setEndOfPlayingDate(movieDto.getEndOfPlayingDate());
        movie.setMinimumAgeToWatch(movieDto.getMinimumAgeToWatch());

        Movie updatedMovie = movieRepository.save(movie);

        return modelMapper.map(updatedMovie, MovieDto.class);
    }

    @Override
    public void deleteMovie(Long movieId) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(()-> new RuntimeException("Movie not found"));

        movieRepository.delete(movie);
    }

    @Override
    public void deleteAllMovies() {
        movieRepository.deleteAll();
    }

    @Override
    public void deleteExpiredMovies() {
        LocalDate today = LocalDate.now();
        movieRepository.deleteMoviesByEndOfPlayingDateBefore(today);
    }
}

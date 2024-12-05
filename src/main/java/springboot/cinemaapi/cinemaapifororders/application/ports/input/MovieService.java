package springboot.cinemaapi.cinemaapifororders.application.ports.input;

import org.springframework.data.domain.Page;
import springboot.cinemaapi.cinemaapifororders.application.dto.movie.MovieRequest;
import springboot.cinemaapi.cinemaapifororders.application.dto.movie.MovieResponse;
import springboot.cinemaapi.cinemaapifororders.domain.enums.MovieCategory;

import java.util.List;
import java.util.UUID;

public interface MovieService {
    Page<MovieResponse> findAllMovies(Integer page, Integer size);
    MovieResponse findMovieById(String movieId);
    List<MovieResponse> findMoviesByMovieCategory(MovieCategory movieCategory);
    MovieResponse addMovie(MovieRequest movieDto);
    MovieResponse updateMovie(String movieId, MovieRequest movieDto);
    void deleteMovie(String movieId);
    void deleteAllMovies();
    void deleteExpiredMovies();
}

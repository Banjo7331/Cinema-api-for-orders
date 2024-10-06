package springboot.cinemaapi.cinemaapifororders.service.movie;

import springboot.cinemaapi.cinemaapifororders.payload.dto.movie.MovieDto;
import springboot.cinemaapi.cinemaapifororders.payload.enums.MovieCategory;

import java.util.List;

public interface MovieService {
    List<MovieDto> getAllMovies();
    MovieDto getMovieById(Long movieId);
    List<MovieDto> getMoviesByMovieCategory(MovieCategory movieCategory);
    MovieDto addMovie(MovieDto movieDto);
    MovieDto updateMovie(Long movieId,MovieDto movieDto);
    void deleteMovie(Long movieId);
    void deleteAllMovies();
    void deleteExpiredMovies();
}

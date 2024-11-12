package springboot.cinemaapi.cinemaapifororders.application.ports.input;

import org.springframework.data.domain.Page;
import springboot.cinemaapi.cinemaapifororders.application.dto.MovieDto;
import springboot.cinemaapi.cinemaapifororders.domain.enums.MovieCategory;

import java.util.List;

public interface MovieService {
    Page<MovieDto> findAllMovies(Integer page, Integer size);
    MovieDto findMovieById(Long movieId);
    List<MovieDto> findMoviesByMovieCategory(MovieCategory movieCategory);
    MovieDto addMovie(MovieDto movieDto);
    MovieDto updateMovie(Long movieId,MovieDto movieDto);
    void deleteMovie(Long movieId);
    void deleteAllMovies();
    void deleteExpiredMovies();
}

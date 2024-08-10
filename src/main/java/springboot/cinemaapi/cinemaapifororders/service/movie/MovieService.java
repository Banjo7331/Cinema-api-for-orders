package springboot.cinemaapi.cinemaapifororders.service.movie;

import springboot.cinemaapi.cinemaapifororders.entity.reservation.Movie;
import springboot.cinemaapi.cinemaapifororders.payload.dto.movie.MovieDto;

import java.util.List;

public interface MovieService {
    List<MovieDto> getAllMovies();
    MovieDto getMovieById(Long movieId);
    MovieDto addMovie(MovieDto movieDto);
    MovieDto updateMovie(Long movieId,MovieDto movieDto);
    void deleteMovie(Long movieId);
    void deleteAllMovies();
}

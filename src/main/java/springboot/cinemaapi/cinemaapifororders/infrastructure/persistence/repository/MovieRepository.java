package springboot.cinemaapi.cinemaapifororders.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import springboot.cinemaapi.cinemaapifororders.domain.model.Movie;
import springboot.cinemaapi.cinemaapifororders.domain.enums.MovieCategory;

import java.time.LocalDate;
import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Modifying
    void deleteMoviesByEndOfPlayingDateBefore(LocalDate endOfPlayingDate);

    @Query("SELECT m FROM Movie m WHERE m.premiereDate <= :currentDate")
    List<Movie> findMoviesAlreadyRunning(@Param("currentDate") LocalDate currentDate);

    List<Movie> findMoviesByMovieCategory(MovieCategory movieCategory);

    Movie findByName(String movieName);
}

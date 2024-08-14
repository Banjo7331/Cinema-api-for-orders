package springboot.cinemaapi.cinemaapifororders.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import springboot.cinemaapi.cinemaapifororders.entity.reservation.Movie;

import java.time.LocalDate;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Transactional
    void deleteMoviesByEndOfPlayingDateBefore(LocalDate endOfPlayingDate);
}

package springboot.cinemaapi.cinemaapifororders.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import springboot.cinemaapi.cinemaapifororders.entity.order.Product;
import springboot.cinemaapi.cinemaapifororders.entity.reservation.Movie;

import java.time.LocalDate;
import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Transactional
    void deleteMoviesByEndOfPlayingDateBefore(LocalDate endOfPlayingDate);

    @Query("SELECT m FROM Movie m WHERE m.premiereDate <= :currentDate")
    List<Movie> findMoviesAlreadyRunning(@Param("currentDate") LocalDate currentDate);
}

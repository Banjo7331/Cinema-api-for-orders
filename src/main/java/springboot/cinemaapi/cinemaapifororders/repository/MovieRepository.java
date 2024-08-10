package springboot.cinemaapi.cinemaapifororders.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springboot.cinemaapi.cinemaapifororders.entity.reservation.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}

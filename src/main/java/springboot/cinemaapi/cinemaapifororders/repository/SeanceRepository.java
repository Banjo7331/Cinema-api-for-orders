package springboot.cinemaapi.cinemaapifororders.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springboot.cinemaapi.cinemaapifororders.entity.reservation.Seance;

import java.util.List;

public interface SeanceRepository extends JpaRepository<Seance, Long> {
    List<Seance> findByRepertoireId(Long repertoireId);
    List<Long> findSeanceIdsByMovieId( Long movieId);
}

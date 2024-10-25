package springboot.cinemaapi.cinemaapifororders.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import springboot.cinemaapi.cinemaapifororders.entity.reservation.Seance;

import java.util.List;

public interface SeanceRepository extends JpaRepository<Seance, Long> {
    Page<Seance> findByRepertoireId(Long repertoireId, Pageable pageable);
    List<Long> findSeanceIdsByMovieId( Long movieId);
}

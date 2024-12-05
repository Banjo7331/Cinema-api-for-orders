package springboot.cinemaapi.cinemaapifororders.infrastructure.persistence.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import springboot.cinemaapi.cinemaapifororders.domain.model.repertoire.Seance;

import java.util.List;
import java.util.UUID;

public interface SeanceRepository extends JpaRepository<Seance, String> {
    Page<Seance> findByRepertoireId(String repertoireId, Pageable pageable);
    List<String> findSeanceIdsByMovieId(String movieId);
}

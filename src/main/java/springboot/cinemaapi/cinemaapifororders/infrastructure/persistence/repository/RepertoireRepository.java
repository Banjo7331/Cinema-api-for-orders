package springboot.cinemaapi.cinemaapifororders.infrastructure.persistence.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import springboot.cinemaapi.cinemaapifororders.domain.model.repertoire.Repertoire;

import java.time.LocalDate;

public interface RepertoireRepository extends JpaRepository<Repertoire, Long> {

    Repertoire findByDate(LocalDate date);

    @Query("SELECT r FROM Repertoire r WHERE r.date BETWEEN :startDate AND :endDate")
    Page<Repertoire> findAllBetweenDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, Pageable pageable);

    @Modifying
    @Query("DELETE FROM Repertoire r WHERE r.date < :date")
    void deleteAllWithDateBefore(@Param("date") LocalDate date);




}

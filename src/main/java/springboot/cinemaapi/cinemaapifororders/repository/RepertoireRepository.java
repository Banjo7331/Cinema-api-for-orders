package springboot.cinemaapi.cinemaapifororders.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import springboot.cinemaapi.cinemaapifororders.entity.reservation.Repertoire;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface RepertoireRepository extends JpaRepository<Repertoire, Long> {

    Repertoire findByDate(LocalDate date);

    @Query("SELECT r FROM repertoir_full_day r WHERE r.date BETWEEN :startDate AND :endDate")
    List<Repertoire> findAllBetweenDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Modifying
    @Transactional
    @Query("DELETE FROM repertoir_full_day r WHERE r.date < :date")
    void deleteAllWithDateBefore(@Param("date") LocalDate date);

}

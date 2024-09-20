package springboot.cinemaapi.cinemaapifororders.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import springboot.cinemaapi.cinemaapifororders.entity.reservation.Reservation;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findAllByUserId(Long userId);

    void deleteAllByUserId(Long userId);

    @Modifying
    @Query("DELETE FROM Reservation r WHERE r.seance.id IN :seanceIds")
    void deleteBySeanceIds(@Param("seanceIds") List<Long> seanceIds);

    @Modifying
    @Query("DELETE FROM Reservation r WHERE r.seance.id = :seanceId")
    void deleteBySeanceId(@Param("seanceId") Long seanceId);
}

package springboot.cinemaapi.cinemaapifororders.infrastructure.persistence.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import springboot.cinemaapi.cinemaapifororders.domain.model.Reservation;
import springboot.cinemaapi.cinemaapifororders.domain.model.repertoire.Seance;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Page<Reservation> findReservationsBySeance(Seance seance, Pageable pageable);
    List<Reservation> findReservationsBySeance(Seance seance);

    Page<Reservation> findAllByUserId(Long userId,Pageable pageable);

    List<Reservation> findAllByUserId(Long userId);

    void deleteAllByUserId(Long userId);

    Page<Reservation> findAllByEmail(String email,Pageable pageable);

    Page<Reservation> findAllByPhoneNumber(String phoneNumber,Pageable pageable);

    @Modifying
    @Query("DELETE FROM Reservation r WHERE r.seance.id IN :seanceIds")
    void deleteBySeanceIds(@Param("seanceIds") List<Long> seanceIds);

    @Modifying
    @Query("DELETE FROM Reservation r WHERE r.seance.id = :seanceId")
    void deleteBySeanceId(@Param("seanceId") Long seanceId);

    @Query("SELECT COUNT(r) FROM Reservation r WHERE r.email = :email AND r.dateCreated >= :startOfDay")
    int countReservationsByEmailAndDate(@Param("email") String email, @Param("startOfDay") LocalDateTime startOfDay);
}

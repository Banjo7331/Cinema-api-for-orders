package springboot.cinemaapi.cinemaapifororders.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springboot.cinemaapi.cinemaapifororders.domain.model.room.Seat;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findAllByRoomId(Long roomId);
}

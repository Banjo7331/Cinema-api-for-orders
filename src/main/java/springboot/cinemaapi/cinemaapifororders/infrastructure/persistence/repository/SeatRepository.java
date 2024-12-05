package springboot.cinemaapi.cinemaapifororders.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springboot.cinemaapi.cinemaapifororders.domain.model.room.Seat;

import java.util.List;
import java.util.UUID;

public interface SeatRepository extends JpaRepository<Seat, String> {
    List<Seat> findAllByRoomId(String roomId);
}

package springboot.cinemaapi.cinemaapifororders.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springboot.cinemaapi.cinemaapifororders.domain.model.room.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {
}

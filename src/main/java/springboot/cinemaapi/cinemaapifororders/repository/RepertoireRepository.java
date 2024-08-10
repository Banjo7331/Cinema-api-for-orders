package springboot.cinemaapi.cinemaapifororders.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springboot.cinemaapi.cinemaapifororders.entity.reservation.Repertoire;

import java.util.Date;

public interface RepertoireRepository extends JpaRepository<Repertoire, Long> {

    Repertoire findByDate(Date date);
}

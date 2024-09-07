package springboot.cinemaapi.cinemaapifororders.service.reservation;

import springboot.cinemaapi.cinemaapifororders.entity.reservation.Reservation;
import springboot.cinemaapi.cinemaapifororders.payload.dto.reservation.ReservationDto;

import java.util.List;

public interface ReservationService {
    ReservationDto createReservation(ReservationDto reservation);
    List<ReservationDto> getAllReservations();
    ReservationDto getReservationById(Long id);

}

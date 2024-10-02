package springboot.cinemaapi.cinemaapifororders.service.reservation;

import springboot.cinemaapi.cinemaapifororders.entity.reservation.Reservation;
import springboot.cinemaapi.cinemaapifororders.payload.dto.reservation.ReservationDto;

import java.util.List;

public interface ReservationService {
    ReservationDto createReservation(ReservationDto reservation);
    List<ReservationDto> findReservationsByEmail(String email);

    List<ReservationDto> findReservationsByPhoneNumber(String phoneNumber);

    List<ReservationDto> getAllReservationsForUser(Long id);

    List<ReservationDto> getAllReservations();

    ReservationDto getReservationById(Long id);

    ReservationDto updateReservation(ReservationDto reservation, Long id);

    void deleteReservationById(Long id);

    void deleteAllUserReservations(Long id);

    void deleteReservationsForSeance(Long id);

    void deleteReservationsForMovie(String id);



}

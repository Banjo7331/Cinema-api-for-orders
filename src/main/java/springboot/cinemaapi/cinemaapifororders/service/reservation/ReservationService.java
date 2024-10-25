package springboot.cinemaapi.cinemaapifororders.service.reservation;

import org.springframework.data.domain.Page;
import springboot.cinemaapi.cinemaapifororders.payload.dto.reservation.ReservationDto;

import java.util.List;

public interface ReservationService {
    ReservationDto addReservation(ReservationDto reservation);
    Page<ReservationDto> findReservationsByEmail(String email,Integer page, Integer size);

    Page<ReservationDto> findReservationsByPhoneNumber(String phoneNumber,Integer page, Integer size);

    Page<ReservationDto> findReservationsBySeanceId(Long repertoireId, Long seanceId, Integer page, Integer size);

    Page<ReservationDto> findAllReservationsForUser(Long id,Integer page, Integer size);

    Page<ReservationDto> findAllReservations(Integer page, Integer size);

    ReservationDto findReservationById(Long id);

    ReservationDto updateReservation(ReservationDto reservation, Long id);

    void deleteReservationById(Long id);

    void deleteAllUserReservations(Long id);

    void deleteReservationsForSeance(Long id);

    void deleteReservationsForMovie(String id);



}

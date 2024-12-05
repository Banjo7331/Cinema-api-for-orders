package springboot.cinemaapi.cinemaapifororders.application.ports.input;

import org.springframework.data.domain.Page;
import springboot.cinemaapi.cinemaapifororders.application.dto.reservation.ReservationRequest;
import springboot.cinemaapi.cinemaapifororders.application.dto.reservation.ReservationResponse;

import java.util.UUID;

public interface ReservationService {
    ReservationResponse addReservation(ReservationRequest reservation);
    Page<ReservationResponse> findReservationsByEmail(String email,Integer page, Integer size);

    Page<ReservationResponse> findReservationsByPhoneNumber(String phoneNumber,Integer page, Integer size);

    Page<ReservationResponse> findReservationsBySeanceId(String repertoireId, String seanceId, Integer page, Integer size);

    Page<ReservationResponse> findAllReservationsForUser(String id,Integer page, Integer size);

    Page<ReservationResponse> findAllReservations(Integer page, Integer size);

    ReservationResponse findReservationById(String id);

    ReservationResponse updateReservation(ReservationRequest reservation, String id);

    void deleteReservationById(String id);

    void deleteAllUserReservations(String id);

    void deleteReservationsForSeance(String id);

    void deleteReservationsForMovie(String id);



}

package springboot.cinemaapi.cinemaapifororders.infrastructure.external.email;

import springboot.cinemaapi.cinemaapifororders.domain.model.Reservation;

import java.util.List;

public interface EmailService {
    void notifyReservationDeletion(List<Reservation> reservations, String subject, String message);
}

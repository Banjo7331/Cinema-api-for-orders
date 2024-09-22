package springboot.cinemaapi.cinemaapifororders.external.service;

import jakarta.mail.MessagingException;
import org.springframework.web.multipart.MultipartFile;
import springboot.cinemaapi.cinemaapifororders.entity.reservation.Reservation;
import springboot.cinemaapi.cinemaapifororders.entity.reservation.Seance;

import java.io.IOException;
import java.util.List;

public interface EmailService {
    void notifyReservationDeletion(List<Reservation> reservations, String subject, String message);
}

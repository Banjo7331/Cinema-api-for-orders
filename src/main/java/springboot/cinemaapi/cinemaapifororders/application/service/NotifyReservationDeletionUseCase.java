package springboot.cinemaapi.cinemaapifororders.application.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import springboot.cinemaapi.cinemaapifororders.application.ports.output.EmailSendlerPort;
import springboot.cinemaapi.cinemaapifororders.domain.model.Reservation;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotifyReservationDeletionUseCase {
    private EmailSendlerPort emailSendlerPort;

    public NotifyReservationDeletionUseCase(EmailSendlerPort emailSendlerPort) {
       this.emailSendlerPort = emailSendlerPort;
    }

    public void notifyReservationDeletion(List<Reservation> reservations, String subject, String message) {

        List<String> reservationEmails = reservations
                .stream()
                .map(Reservation::getEmail)
                .collect(Collectors.toList());

        emailSendlerPort.sendEmail(
                new MultipartFile[]{},
                "",
                reservationEmails.toArray(new String[0]),
                subject,
                message
        );

    }
}

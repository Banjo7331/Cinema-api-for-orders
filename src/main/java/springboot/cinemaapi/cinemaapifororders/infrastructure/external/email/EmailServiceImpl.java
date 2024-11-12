package springboot.cinemaapi.cinemaapifororders.infrastructure.external.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import springboot.cinemaapi.cinemaapifororders.domain.model.Reservation;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class EmailServiceImpl implements EmailService {

    @Value("${spring.mail.username}")
    private String fromEmail;

    private JavaMailSender mailSender;

    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.mailSender = javaMailSender;
    }

    private void sendEmail(MultipartFile[] files, String to,String[] cc, String subject, String body){
        try {
            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);


            messageHelper.setFrom(fromEmail);
            messageHelper.setTo(to);
            messageHelper.setCc(cc);
            messageHelper.setSubject(subject);
            messageHelper.setText(body);

            for (MultipartFile file : files) {
                messageHelper.addAttachment(Objects.requireNonNull(file.getOriginalFilename()), new ByteArrayResource(file.getBytes()));
            }

            mailSender.send(message);

        }catch (MessagingException | IOException e){
            throw new RuntimeException(e);
        }

    }


    @Override
    public void notifyReservationDeletion(List<Reservation> reservations, String subject, String message) {

        List<String> reservationEmails = reservations
                .stream()
                .map(Reservation::getEmail)
                .collect(Collectors.toList());

        sendEmail(
                new MultipartFile[]{},
                "",
                reservationEmails.toArray(new String[0]),
                subject,
                message
        );

    }
}

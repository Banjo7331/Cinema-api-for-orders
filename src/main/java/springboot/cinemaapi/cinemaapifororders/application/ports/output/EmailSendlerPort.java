package springboot.cinemaapi.cinemaapifororders.application.ports.output;

import org.springframework.web.multipart.MultipartFile;

public interface EmailSendlerPort {
    void sendEmail(MultipartFile[] files, String to, String[] cc, String subject, String body);
}

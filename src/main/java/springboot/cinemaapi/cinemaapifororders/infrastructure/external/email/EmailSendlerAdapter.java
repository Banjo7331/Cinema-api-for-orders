package springboot.cinemaapi.cinemaapifororders.infrastructure.external.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import springboot.cinemaapi.cinemaapifororders.application.ports.output.EmailSendlerPort;

import java.io.IOException;
import java.util.Objects;

@Component
public class EmailSendlerAdapter implements EmailSendlerPort {

    @Value("${spring.mail.username}")
    private String fromEmail;

    private JavaMailSender mailSender;

    public EmailSendlerAdapter(JavaMailSender mailSender) {this.mailSender = mailSender;}

    public void sendEmail(MultipartFile[] files, String to, String[] cc, String subject, String body){
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
}

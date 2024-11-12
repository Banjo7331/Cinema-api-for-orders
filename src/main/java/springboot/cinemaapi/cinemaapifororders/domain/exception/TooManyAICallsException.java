package springboot.cinemaapi.cinemaapifororders.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;

@ResponseStatus(value = HttpStatus.TOO_MANY_REQUESTS)
public class TooManyAICallsException extends RuntimeException{
    private LocalDateTime hourToNextPossibleCall;

    public TooManyAICallsException(String message) {
        super(message);
        this.hourToNextPossibleCall = LocalDateTime.now().plusHours(1);
    }

    public LocalDateTime getHourToNextPossibleCall() {
        return hourToNextPossibleCall;
    }
}

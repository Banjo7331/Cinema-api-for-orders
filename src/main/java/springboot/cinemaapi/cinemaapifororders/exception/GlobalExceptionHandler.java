package springboot.cinemaapi.cinemaapifororders.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDate;
import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(HttpClientErrorException.class)
  public ResponseEntity<ErrorDetails> handleTooManyAiCallsException(TooManyAICallsException exception, WebRequest request) {

    ErrorDetails errorDetails = new ErrorDetails(
            exception.getMessage(),
            LocalDate.now(),
            request.getDescription(false)
    );
    return new ResponseEntity<>(errorDetails, HttpStatus.TOO_MANY_REQUESTS);
  }

  @ExceptionHandler(ReservationLimitExceededException.class)
  public ResponseEntity<ErrorDetails> handleReservationLimitExceededException(ReservationLimitExceededException exception, WebRequest request) {


    ErrorDetails errorDetails = new ErrorDetails(
            exception.getMessage(),
            LocalDate.now(),
            request.getDescription(false)
    );
    return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
  }
}

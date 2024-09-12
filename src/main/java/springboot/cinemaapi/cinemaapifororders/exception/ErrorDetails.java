package springboot.cinemaapi.cinemaapifororders.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@AllArgsConstructor
public class ErrorDetails {
    private String message;
    private LocalDate timestamp;
    private String details;

}

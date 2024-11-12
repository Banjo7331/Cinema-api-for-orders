package springboot.cinemaapi.cinemaapifororders.domain.exception;

import lombok.AllArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
public class ErrorDetails {
    private String message;
    private LocalDate timestamp;
    private String details;

}

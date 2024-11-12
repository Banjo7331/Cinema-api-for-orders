package springboot.cinemaapi.cinemaapifororders.domain.exception;

public class ReservationLimitExceededException extends RuntimeException {
    public ReservationLimitExceededException(String message) {
        super(message);
    }
}

package springboot.cinemaapi.cinemaapifororders.exception;

public class ReservationLimitExceededException extends RuntimeException {
    public ReservationLimitExceededException(String message) {
        super(message);
    }
}

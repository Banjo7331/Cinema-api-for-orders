package springboot.cinemaapi.cinemaapifororders.application.dto.reservation;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationRequest {

    @NotNull(message = "Email is required")
    @Email(message = "Invalid email address")
    private String email;

    @Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$", message = "Invalid phone number format")
    private String phoneNumber;

    private boolean attendance;

    @NotNull(message = "seanceId is required")
    private String seanceId;

    private String userId;

    private String orderId;

    @NotEmpty(message = "numberOfViewers is required")
    @Size(min = 1, message = "numberOfViewers must be at least 1")
    private List<String> seatIds;
}

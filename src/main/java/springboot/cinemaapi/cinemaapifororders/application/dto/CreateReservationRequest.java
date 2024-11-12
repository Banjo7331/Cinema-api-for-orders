package springboot.cinemaapi.cinemaapifororders.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateReservationRequest {

    @NotNull(message = "numberOfViewers is required")
    @Min(value = 1, message = "numberOfViewers must be at least 1")
    private Integer numberOfViewers;

    @NotNull(message = "Email is required")
    @Email(message = "Invalid email address")
    private String email;

    @Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$", message = "Invalid phone number format")
    private String phoneNumber;

    @NotNull(message = "seanceId is required")
    private Long seanceId;

    private Long userId;

    private Long orderId;
}

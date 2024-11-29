package springboot.cinemaapi.cinemaapifororders.application.dto.auth;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDto {
    private String username;
    private String email;

    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "Password must be at least 8 characters long, contain at least one uppercase letter and one digit")
    private String password;
    private String phoneNumber;
}

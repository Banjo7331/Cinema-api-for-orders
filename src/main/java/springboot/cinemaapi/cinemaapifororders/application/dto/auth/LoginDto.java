package springboot.cinemaapi.cinemaapifororders.application.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {
    private Long id;
    private String usernameOrEmail;
    private String password;
}

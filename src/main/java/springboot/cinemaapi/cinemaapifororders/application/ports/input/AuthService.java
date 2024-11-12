package springboot.cinemaapi.cinemaapifororders.application.ports.input;

import springboot.cinemaapi.cinemaapifororders.application.dto.auth.LoginDto;
import springboot.cinemaapi.cinemaapifororders.application.dto.auth.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);
    String register(RegisterDto registerDto);
}

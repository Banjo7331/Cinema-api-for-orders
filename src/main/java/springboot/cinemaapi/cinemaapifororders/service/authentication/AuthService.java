package springboot.cinemaapi.cinemaapifororders.service.authentication;

import springboot.cinemaapi.cinemaapifororders.payload.dto.LoginDto;
import springboot.cinemaapi.cinemaapifororders.payload.dto.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);
    String register(RegisterDto registerDto);
}

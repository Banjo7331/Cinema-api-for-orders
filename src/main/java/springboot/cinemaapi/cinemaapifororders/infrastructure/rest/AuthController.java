package springboot.cinemaapi.cinemaapifororders.infrastructure.rest;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springboot.cinemaapi.cinemaapifororders.application.dto.auth.JwtAuthResponse;
import springboot.cinemaapi.cinemaapifororders.application.dto.auth.LoginDto;
import springboot.cinemaapi.cinemaapifororders.application.dto.auth.RegisterDto;
import springboot.cinemaapi.cinemaapifororders.application.ports.input.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    @PostMapping(value = {"/login","/signin"})
    public ResponseEntity<JwtAuthResponse> login(@Valid @RequestBody LoginDto loginDto) {
        String token = authService.login(loginDto);

        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(token);
        return ResponseEntity.ok(jwtAuthResponse);
    }

    @PostMapping(value = {"/logout","/signout"})
    public ResponseEntity<JwtAuthResponse> logout(@Valid @RequestBody LoginDto loginDto) {
        String token = authService.login(loginDto);

        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(token);
        return ResponseEntity.ok(jwtAuthResponse);
    }

    @PostMapping(value = {"/register","/singup"})
    public ResponseEntity<String> register(@Valid @RequestBody RegisterDto registerDto) {
        System.out.println("test");
        String response = authService.register(registerDto);
        return ResponseEntity.ok(response);
    }
}

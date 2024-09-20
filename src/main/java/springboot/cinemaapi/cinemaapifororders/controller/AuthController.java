package springboot.cinemaapi.cinemaapifororders.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springboot.cinemaapi.cinemaapifororders.payload.JwtAuthResponse;
import springboot.cinemaapi.cinemaapifororders.payload.dto.LoginDto;
import springboot.cinemaapi.cinemaapifororders.payload.dto.RegisterDto;
import springboot.cinemaapi.cinemaapifororders.service.authentication.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    @PostMapping(value = {"/login","/signin"})
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto) {
        String token = authService.login(loginDto);

        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(token);
        return ResponseEntity.ok(jwtAuthResponse);
    }

    @PostMapping(value = {"/logout","/signout"})
    public ResponseEntity<JwtAuthResponse> logout(@RequestBody LoginDto loginDto) {
        String token = authService.login(loginDto);

        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(token);
        return ResponseEntity.ok(jwtAuthResponse);
    }

    @PostMapping(value = {"/register","/singup"})
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
        System.out.println("test");
        String response = authService.register(registerDto);
        return ResponseEntity.ok(response);
    }
}

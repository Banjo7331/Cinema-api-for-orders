package springboot.cinemaapi.cinemaapifororders.external.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import springboot.cinemaapi.cinemaapifororders.payload.CinemaAiAnswer;
import springboot.cinemaapi.cinemaapifororders.payload.dto.UserPreferencesDto;
import springboot.cinemaapi.cinemaapifororders.external.service.AiMovieAdvicerService;

@RestController
@RequestMapping("/ai-advicer")
public class AiMovieAdvicerController {
    private AiMovieAdvicerService aiMovieAdvicerService;

    public AiMovieAdvicerController(AiMovieAdvicerService aiMovieAdvicerService) {
        this.aiMovieAdvicerService = aiMovieAdvicerService;
    }
//    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('EMPLOYER') or hasRole('USER')")
    @PostMapping("/newTest")
    public ResponseEntity<CinemaAiAnswer> getOpenAiResponse(@RequestBody UserPreferencesDto userPreferences){

        String model = "gpt-4";

        return ResponseEntity.ok(aiMovieAdvicerService.askAi(userPreferences,model));
    }
}

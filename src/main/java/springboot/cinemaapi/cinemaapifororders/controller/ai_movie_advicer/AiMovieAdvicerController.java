package springboot.cinemaapi.cinemaapifororders.controller.ai_movie_advicer;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.cinemaapi.cinemaapifororders.payload.CinemaAiAnswer;
import springboot.cinemaapi.cinemaapifororders.payload.dto.UserPreferencesDto;
import springboot.cinemaapi.cinemaapifororders.service.ai_movie_advicer.AiMovieAdvicerService;

@RestController
@RequestMapping("/ai-advicer")
public class AiMovieAdvicerController {
    private AiMovieAdvicerService aiMovieAdvicerService;

    public AiMovieAdvicerController(AiMovieAdvicerService aiMovieAdvicerService) {
        this.aiMovieAdvicerService = aiMovieAdvicerService;
    }

    @PostMapping("/newTest")
    public ResponseEntity<CinemaAiAnswer> getOpenAiResponse(@RequestBody UserPreferencesDto userPreferences){

        String model = "gpt-4";

        return ResponseEntity.ok(aiMovieAdvicerService.askAi(userPreferences,model));
    }
}

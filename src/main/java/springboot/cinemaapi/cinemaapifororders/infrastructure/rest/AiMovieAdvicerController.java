package springboot.cinemaapi.cinemaapifororders.infrastructure.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import springboot.cinemaapi.cinemaapifororders.infrastructure.external.ai.dto.CinemaAiAnswer;
import springboot.cinemaapi.cinemaapifororders.application.service.GetAiMovieRecommendationUseCase;
import springboot.cinemaapi.cinemaapifororders.infrastructure.external.ai.dto.UserPreferencesDto;

@RestController
@RequestMapping("/ai-advicer")
public class AiMovieAdvicerController {
    private GetAiMovieRecommendationUseCase getAiMovieRecommendationUseCase;

    public AiMovieAdvicerController(GetAiMovieRecommendationUseCase getAiMovieRecommendationUseCase) {
        this.getAiMovieRecommendationUseCase = getAiMovieRecommendationUseCase;
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('EMPLOYER') or hasRole('USER')")
    @PostMapping("/newTest")
    public ResponseEntity<CinemaAiAnswer> getOpenAiResponse(@RequestBody UserPreferencesDto userPreferences){

        String model = "gpt-4";

        return ResponseEntity.ok(getAiMovieRecommendationUseCase.getMovieRecommendations(userPreferences,model));
    }
}

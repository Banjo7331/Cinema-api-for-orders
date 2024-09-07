package springboot.cinemaapi.cinemaapifororders.controller.ai_movie_advicer;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.cinemaapi.cinemaapifororders.payload.dto.UserPreferencesDto;
import springboot.cinemaapi.cinemaapifororders.service.ai_movie_advicer.AiMovieAdvicerService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ai-advicer")
public class AiMovieAdvicerController {
    private AiMovieAdvicerService aiMovieAdvicerService;

    public AiMovieAdvicerController(AiMovieAdvicerService aiMovieAdvicerService) {
        this.aiMovieAdvicerService = aiMovieAdvicerService;
    }

    @PostMapping("/ask-ai")
    public ResponseEntity<List<Map<String, String>>> askAI(@RequestBody UserPreferencesDto userPreferences) {
        String systemContent = "You are a helpful assistant for Cinema to tell the Client which movie is for him to watch Based on clients preferences";
        String model = "gpt-4";

        List<Map<String, String>> aiRecommendations = aiMovieAdvicerService.getChatCompletion(model, systemContent, userPreferences);

        return ResponseEntity.ok(aiRecommendations);
    }
}

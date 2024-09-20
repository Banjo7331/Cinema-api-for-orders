package springboot.cinemaapi.cinemaapifororders.service.ai_movie_advicer;

import springboot.cinemaapi.cinemaapifororders.payload.CinemaAiAnswer;
import springboot.cinemaapi.cinemaapifororders.payload.dto.UserPreferencesDto;

import java.util.List;
import java.util.Map;

public interface AiMovieAdvicerService {
    CinemaAiAnswer askAi(UserPreferencesDto userPreferences,String model);
}

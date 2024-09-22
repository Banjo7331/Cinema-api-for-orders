package springboot.cinemaapi.cinemaapifororders.external.service;

import springboot.cinemaapi.cinemaapifororders.payload.CinemaAiAnswer;
import springboot.cinemaapi.cinemaapifororders.payload.dto.UserPreferencesDto;

public interface AiMovieAdvicerService {
    CinemaAiAnswer askAi(UserPreferencesDto userPreferences,String model);
}

package springboot.cinemaapi.cinemaapifororders.application.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import springboot.cinemaapi.cinemaapifororders.infrastructure.external.ai.dto.ChatCompletionRequest;
import springboot.cinemaapi.cinemaapifororders.infrastructure.external.ai.dto.ChatCompletionResponse;
import springboot.cinemaapi.cinemaapifororders.infrastructure.external.ai.dto.CinemaAiAnswer;
import springboot.cinemaapi.cinemaapifororders.application.ports.output.AiDataFetcherPort;
import springboot.cinemaapi.cinemaapifororders.infrastructure.persistence.repository.MovieRepository;
import springboot.cinemaapi.cinemaapifororders.infrastructure.external.ai.dto.UserPreferencesDto;

@Service
public class GetAiMovieRecommendationUseCase {

    private final AiDataFetcherPort aiDataFetcherPort;
    private MovieRepository movieRepository;

    public GetAiMovieRecommendationUseCase(AiDataFetcherPort aiDataFetcherPort, MovieRepository movieRepository) {
        this.aiDataFetcherPort = aiDataFetcherPort;
        this.movieRepository = movieRepository;
    }

    public CinemaAiAnswer getMovieRecommendations(UserPreferencesDto userPreferences, String model) {
        StringBuilder movieListBuilder = new StringBuilder();

        movieRepository.findMoviesAlreadyRunning(LocalDate.now())
                .forEach(movie -> {
                    movieListBuilder.append("Title: ").append(movie.getName()).append(" ").append(movie.getMovieCategory())
                            .append("\nDescription: ").append(movie.getDescription())
                            .append("\n\n");
                });

        String movieList = movieListBuilder.toString();

        String movieCategory = (userPreferences.getMovieCategory() != null && !userPreferences.getMovieCategory().isEmpty())
                ? userPreferences.getMovieCategory()
                : "Optional";

        String messageToAi = buildMessageToAi(userPreferences, movieCategory, movieList);

        ChatCompletionRequest chatRequest = new ChatCompletionRequest(messageToAi, model);

        ChatCompletionResponse chatResponse = aiDataFetcherPort.fetchAiData(chatRequest);

        return structureAiAnswer(chatResponse, model);
    }

    private String buildMessageToAi(UserPreferencesDto userPreferences, String movieCategory, String movieList) {
        return "Your task is to recommend best matched movies to customer's requirements. Here it is the requirement: "
                + userPreferences + " with preferred category: " + movieCategory
                + ". If the category is optional, only consider client’s requirement.\n"
                + "Here is a list of movies from the cinema database: " + movieList + "\n\n"
                + "Provide the result in JSON format. Return the data in the format {\\\"recommended_movies\\\": "
                + "[{\\\"title\\\": \\\"Movie A\\\", \\\"reason\\\": \\\"Explanation\\\"}]}. "
                + "If no movie matches, return an empty array. If the movie does not meet the 75% threshold, "
                + "do not mention it at all. Return only the movie titles and reasons for the recommendation.";
    }

    private CinemaAiAnswer structureAiAnswer(ChatCompletionResponse chatCompletionResponse, String model) {
        String responseContent = chatCompletionResponse.getChoices().get(0).getMessage().getContent();
        String cleanedResponse = responseContent.replace("```json", "").replace("```", "").trim();

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Map<String, Object> structuredResponse = objectMapper.readValue(cleanedResponse, new TypeReference<>() {});
            List<Map<String, Object>> recommendedMovies = (List<Map<String, Object>>) structuredResponse.get("recommended_movies");

            List<CinemaAiAnswer.Recommendation> recommendations = Optional.ofNullable(recommendedMovies)
                    .orElse(Collections.emptyList())
                    .stream()
                    .map(movie -> new CinemaAiAnswer.Recommendation(
                            (String) movie.get("title"),
                            (String) movie.get("reason")
                    ))
                    .collect(Collectors.toList());

            return new CinemaAiAnswer(recommendations, model);

        } catch (IOException e) {
            throw new RuntimeException("Error processing AI response as JSON", e);
        }
    }
}

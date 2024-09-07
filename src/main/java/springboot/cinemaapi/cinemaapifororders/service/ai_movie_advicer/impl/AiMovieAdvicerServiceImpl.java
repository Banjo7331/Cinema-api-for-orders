package springboot.cinemaapi.cinemaapifororders.service.ai_movie_advicer.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import springboot.cinemaapi.cinemaapifororders.exception.TooManyAICallsException;
import springboot.cinemaapi.cinemaapifororders.payload.dto.UserPreferencesDto;
import springboot.cinemaapi.cinemaapifororders.repository.MovieRepository;
import springboot.cinemaapi.cinemaapifororders.service.ai_movie_advicer.AiMovieAdvicerService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AiMovieAdvicerServiceImpl implements AiMovieAdvicerService {

    @Value("${openai.api.key}")
    private String apiKey;

    private MovieRepository movieRepository;

    public AiMovieAdvicerServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String AIML_API_URL = "https://api.aimlapi.com/v1/chat/completions";

    @Override
    public List<Map<String, String>> getChatCompletion(String model, String systemContent, UserPreferencesDto userPreferences) {

        System.out.println(movieRepository.findMoviesAlreadyRunning(LocalDate.now()));
        List<Map<String, String>> moviesStarted = movieRepository.findMoviesAlreadyRunning(LocalDate.now())
                .stream()
                .map(movie -> {
                    Map<String, String> movieData = new HashMap<>();
                    movieData.put("title&category", movie.getName() +" "+ movie.getCategory());
                    movieData.put("description", movie.getDescription());
                    return movieData;
                })
                .collect(Collectors.toList());

        System.out.println(moviesStarted);

        StringBuilder movieListBuilder = new StringBuilder();
        movieListBuilder.append("Available movies:\n");
        for (Map<String, String> movie : moviesStarted) {
            movieListBuilder.append("Title: ").append(movie.get("title&category"))
                    .append("\nDescription: ").append(movie.get("description"))
                    .append("\n\n");
        }
        String beginningOfPrompt ="The client prefers: " + userPreferences.getPreferenceForMovieType();

        if(!userPreferences.getMovieCategory().isEmpty()){
            beginningOfPrompt = "The client prefers movie of category: "+userPreferences.getMovieCategory()+"with his preferences" + userPreferences.getPreferenceForMovieType();
        }
        String userMessage = beginningOfPrompt +
                ". Please compare each movie description with this client preference. Recommend directly to customer ONLY movies where the accuracy is at least 75%. Provide the result in JSON format."+ "Return the data in the format {\\\"recommended_movies\\\": [{\\\"title\\\": \\\"Movie A\\\", \\\"reason\\\": \\\"Explanation\\\"}]}."+ " If no movie matches, return an empty array.\" If the movie does not meet the 75% threshold, do not mention it at all. Return only the movie titles and reasons for the recommendation.\n\n There are the following movies:\n" + movieListBuilder.toString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.set("Content-Type", "application/json");

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", model);
        requestBody.put("messages", new Object[]{
                new HashMap<String, String>() {{
                    put("role", "system");
                    put("content", systemContent);
                }},
                new HashMap<String, Object>() {{
                    put("role", "user");
                    put("content", "Here is your task:\n\n" + userMessage);
                }}
        });
        requestBody.put("temperature", 0.5);
        requestBody.put("max_tokens", 256);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(AIML_API_URL, HttpMethod.POST, entity, Map.class);
            Map<String, Object> responseBody = response.getBody();
            if (responseBody != null && responseBody.containsKey("choices")) {
                Map<String, Object> firstChoice = (Map<String, Object>) ((List<Object>) responseBody.get("choices")).get(0);
                Map<String, String> message = (Map<String, String>) firstChoice.get("message");

                String aiResponse = message.get("content");
                return processMovieRecommendationResponse(aiResponse);
            } else {
                throw new TooManyAICallsException("Failed to get response from AI/ML API");
            }
        }catch (HttpClientErrorException e) {
            throw new TooManyAICallsException(e.getMessage());
        }

    }

    public List<Map<String, String>> processMovieRecommendationResponse(String aiResponse) {
        // Clean the AI response (remove markdown formatting)
        String cleanedResponse = aiResponse.replaceAll("```json", "").replaceAll("```", "").trim();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> structuredResponse = objectMapper.readValue(cleanedResponse, new TypeReference<Map<String, Object>>() {});

            // Extract the list of recommended movies
            List<Map<String, Object>> recommendedMovies = (List<Map<String, Object>>) structuredResponse.get("recommended_movies");

            // Prepare the response list
            List<Map<String, String>> recommendations = new ArrayList<>();

            if (recommendedMovies != null && !recommendedMovies.isEmpty()) {
                for (Map<String, Object> movie : recommendedMovies) {
                    Map<String, String> movieDetails = new HashMap<>();
                    movieDetails.put("title", (String) movie.get("title"));
                    movieDetails.put("reason", (String) movie.get("reason"));
                    recommendations.add(movieDetails);
                }
            }

            return recommendations; // Return the list of movies with title and reason
        } catch (Exception e) {
            throw new RuntimeException("Error processing AI response as JSON", e);
        }
    }

}

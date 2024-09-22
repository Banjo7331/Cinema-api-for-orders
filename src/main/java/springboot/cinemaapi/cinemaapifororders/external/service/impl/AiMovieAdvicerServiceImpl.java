package springboot.cinemaapi.cinemaapifororders.external.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import springboot.cinemaapi.cinemaapifororders.payload.ChatCompletionRequest;
import springboot.cinemaapi.cinemaapifororders.payload.ChatCompletionResponse;
import springboot.cinemaapi.cinemaapifororders.payload.CinemaAiAnswer;
import springboot.cinemaapi.cinemaapifororders.payload.dto.UserPreferencesDto;
import springboot.cinemaapi.cinemaapifororders.repository.MovieRepository;
import springboot.cinemaapi.cinemaapifororders.external.service.AiMovieAdvicerService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AiMovieAdvicerServiceImpl implements AiMovieAdvicerService {

    private String apiKey;

    private MovieRepository movieRepository;
    private RestTemplate restTemplate;

    public AiMovieAdvicerServiceImpl(MovieRepository movieRepository,RestTemplate restTemplate) {
        this.movieRepository = movieRepository;
        this.restTemplate = restTemplate;
    }


    @Override
    public CinemaAiAnswer askAi(UserPreferencesDto userPreferences, String model){

        StringBuilder movieListBuilder = new StringBuilder();

        movieRepository.findMoviesAlreadyRunning(LocalDate.now())
                .forEach(movie -> {
                    movieListBuilder.append("Title: ").append(movie.getName()).append(" ").append(movie.getCategory())
                            .append("\nDescription: ").append(movie.getDescription())
                            .append("\n\n");
                });



        String movieCategory = "Optional";

        if(userPreferences.getMovieCategory() != null && !userPreferences.getMovieCategory().isEmpty()){
            movieCategory = userPreferences.getMovieCategory();
        }

        StringBuilder messageToAi = new StringBuilder();

        messageToAi.append("Your task is to recommend best matched movies to customer's requirements. Here it is the requirement: ").append(userPreferences).append(" with preferred category: ").append(movieCategory).append(". If the category is optional, only consider client`s requirement").append(".\n")
                .append(". There is a list of movies from cinema database: ").append(movieListBuilder.toString()).append("\n\n")
                .append("Provide the result in JSON format. Return the data in the format {\\\"recommended_movies\\\": [{\\\"title\\\": \\\"Movie A\\\", \\\"reason\\\": \\\"Explanation\\\"}]}.").append(" If no movie matches, return an empty array.\" If the movie does not meet the 75% threshold, do not mention it at all. Return only the movie titles and reasons for the recommendation.\n\n");

        ChatCompletionRequest chatCompletionRequest = new ChatCompletionRequest(messageToAi.toString(),model);

        ChatCompletionResponse chatCompletionResponse = restTemplate.postForObject("https://api.aimlapi.com/v1/chat/completions",chatCompletionRequest,ChatCompletionResponse.class);

        assert chatCompletionResponse != null;
        return structureAiAnswer(chatCompletionResponse,model);
    }

    private CinemaAiAnswer structureAiAnswer(ChatCompletionResponse chatCompletionResponse,String model) {

        String response = chatCompletionResponse.getChoices().get(0).getMessage().getContent();

        String cleanedResponse = response.replace("```json", "").replace("```", "").trim();

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Map<String, Object> structuredResponse = objectMapper.readValue(cleanedResponse, new TypeReference<Map<String, Object>>() {});

            List<Map<String, Object>> recommendedMovies = (List<Map<String, Object>>) structuredResponse.get("recommended_movies");

            List<CinemaAiAnswer.Recommendation> recommendations = Optional.ofNullable(recommendedMovies)
                    .orElse(Collections.emptyList())
                    .stream()
                    .map(movie -> new CinemaAiAnswer.Recommendation(
                            (String) movie.get("title"),
                            (String) movie.get("reason")
                    ))
                    .collect(Collectors.toList());

            return new CinemaAiAnswer(recommendations,model);

        } catch (IOException e) {
            throw new RuntimeException("Error processing AI response as JSON", e);
        }
    }


}

package springboot.cinemaapi.cinemaapifororders.infrastructure.external.ai;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import springboot.cinemaapi.cinemaapifororders.infrastructure.external.ai.dto.ChatCompletionRequest;
import springboot.cinemaapi.cinemaapifororders.infrastructure.external.ai.dto.ChatCompletionResponse;
import springboot.cinemaapi.cinemaapifororders.application.ports.output.AiDataFetcherPort;

@Component
public class AiDataFetcherAdapter implements AiDataFetcherPort {
    @Value("${openai.api.url}")
    private String apiUrl;
    private final RestTemplate restTemplate;


    public AiDataFetcherAdapter(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public ChatCompletionResponse fetchAiData(ChatCompletionRequest request) {
        return restTemplate.postForObject(apiUrl, request, ChatCompletionResponse.class);
    }
}

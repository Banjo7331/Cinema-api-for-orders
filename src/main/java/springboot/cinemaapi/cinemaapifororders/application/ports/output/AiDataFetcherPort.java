package springboot.cinemaapi.cinemaapifororders.application.ports.output;

import springboot.cinemaapi.cinemaapifororders.infrastructure.external.ai.dto.ChatCompletionRequest;
import springboot.cinemaapi.cinemaapifororders.infrastructure.external.ai.dto.ChatCompletionResponse;

public interface AiDataFetcherPort {
    ChatCompletionResponse fetchAiData(ChatCompletionRequest request);
}

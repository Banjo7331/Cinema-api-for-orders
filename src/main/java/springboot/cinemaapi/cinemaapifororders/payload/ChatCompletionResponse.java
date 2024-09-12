package springboot.cinemaapi.cinemaapifororders.payload;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatCompletionResponse {


    private List<Choice> choices;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Choice{
        private int index;

        private ChatMessage message;
    }
}

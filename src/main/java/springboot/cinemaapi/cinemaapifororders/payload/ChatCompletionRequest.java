package springboot.cinemaapi.cinemaapifororders.payload;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatCompletionRequest {
    private String model;

    private List<ChatMessage> messages;

    private int n;

    public ChatCompletionRequest(String prompt, String model) {
        this.messages = new ArrayList<ChatMessage>();
        this.model = model;
        this.n = 3;
        this.messages.add(new ChatMessage("assistant",prompt));
    }
}

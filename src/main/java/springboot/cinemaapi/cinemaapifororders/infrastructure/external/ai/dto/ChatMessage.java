package springboot.cinemaapi.cinemaapifororders.infrastructure.external.ai.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {
    private String role;

    private String content;

}

package springboot.cinemaapi.cinemaapifororders.payload;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {
    private String role;

    private String content;

}

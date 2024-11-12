package springboot.cinemaapi.cinemaapifororders.infrastructure.external.ai.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CinemaAiAnswer {

    private List<Recommendation> recommendations;

    private String model;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Recommendation{
        private String movieName;

        private String argument;
    }


}

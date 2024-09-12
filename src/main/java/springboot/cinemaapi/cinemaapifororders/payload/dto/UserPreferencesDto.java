package springboot.cinemaapi.cinemaapifororders.payload.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserPreferencesDto {
    private String prompt;

    private String movieCategory;
}

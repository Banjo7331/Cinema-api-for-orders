package springboot.cinemaapi.cinemaapifororders.payload.dto.movie;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomDto {

    private Long id;

    private int number;

    private int numberOfRows;

    private boolean special;

    private boolean available;
}

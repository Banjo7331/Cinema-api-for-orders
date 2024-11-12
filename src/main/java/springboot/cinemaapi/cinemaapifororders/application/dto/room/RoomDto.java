package springboot.cinemaapi.cinemaapifororders.application.dto.room;

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

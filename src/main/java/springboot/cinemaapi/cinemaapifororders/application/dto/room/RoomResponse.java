package springboot.cinemaapi.cinemaapifororders.application.dto.room;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomResponse {

    private String id;

    private int number;

    private int numberOfAllSeats;

    private int numberOfRows;

    private boolean special;

    private boolean available;
}

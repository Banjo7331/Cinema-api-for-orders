package springboot.cinemaapi.cinemaapifororders.entity.reservation;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "room")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Min(1)
    @Max(10)
    @Column
    private Integer number;

    @NotNull
    @Min(1)
    @Column
    private Integer numberOfRows;

    @NotNull
    @Column
    private Integer numberOfSeats;

    @NotNull
    @Column
    private Boolean special;

    @NotNull
    @Column
    private Boolean available;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Seat> seats;


}

package springboot.cinemaapi.cinemaapifororders.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import springboot.cinemaapi.cinemaapifororders.domain.model.repertoire.Seance;
import springboot.cinemaapi.cinemaapifororders.domain.enums.MovieCategory;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "movie")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Min(1)
    @Max(240)
    @Column
    private Long lengthInMinutes;

    @NotNull
    @Size(min = 1, max = 30)
    @Column
    private String name;

    @NotNull
    @Column
    private MovieCategory movieCategory;

    @NotNull
    @Size(min = 20, max = 100)
    @Column
    private String description;

    @NotNull
    @PastOrPresent
    @Column
    private LocalDate premiereDate;

    @NotNull
    @FutureOrPresent
    @Column
    private LocalDate endOfPlayingDate;

    @NotNull
    @Min(3)
    @Column
    private Integer minimumAgeToWatch;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Seance> seances;

}

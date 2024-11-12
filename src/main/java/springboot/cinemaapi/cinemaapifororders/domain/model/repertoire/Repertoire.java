package springboot.cinemaapi.cinemaapifororders.domain.model.repertoire;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "repertoir_full_day",uniqueConstraints = {@UniqueConstraint(columnNames = {"date"})})
public class Repertoire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column
    @Temporal(TemporalType.DATE)
    private LocalDate date;

    @OneToMany(mappedBy = "repertoire", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Seance> seancesList;
}

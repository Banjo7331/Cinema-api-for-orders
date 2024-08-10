package springboot.cinemaapi.cinemaapifororders.entity.reservation;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "repertoir_full_day")
public class Repertoire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date date;

    @OneToMany(mappedBy = "repertoire", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Seance> reservationList;
}

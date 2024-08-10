package springboot.cinemaapi.cinemaapifororders.entity.reservation;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer numberOfViewers;

    @Column(nullable = false)
    private String email;

    @Pattern(regexp = "^\\+?[0-9]{1,3}?[-.\\s]?\\(?[0-9]{1,4}?\\)?[-.\\s]?[0-9]{1,4}[-.\\s]?[0-9]{1,9}$",
            message = "Invalid phone number format")
    @Column(nullable = false)
    private String phoneNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "seance_id",nullable = false)
    private Seance seance;
}

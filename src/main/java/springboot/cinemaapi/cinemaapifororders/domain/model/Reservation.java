package springboot.cinemaapi.cinemaapifororders.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import springboot.cinemaapi.cinemaapifororders.domain.model.user.User;
import springboot.cinemaapi.cinemaapifororders.domain.model.order.Order;
import springboot.cinemaapi.cinemaapifororders.domain.model.repertoire.Seance;
import springboot.cinemaapi.cinemaapifororders.domain.model.room.Seat;

import java.util.Date;
import java.util.List;

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

    @NotNull
    @Min(value = 1, message = "numberOfViewers must be at least 1")
    @Column
    private Integer numberOfViewers;

    @NotNull
    @Email(message = "Invalid email address")
    @Column
    private String email;

    @Pattern(regexp = "^\\+?[0-9]{1,3}?[-.\\s]?\\(?[0-9]{1,4}?\\)?[-.\\s]?[0-9]{1,4}[-.\\s]?[0-9]{1,9}$",
            message = "Invalid phone number format")
    @Column(nullable = false)
    private String phoneNumber;

    @NotNull
    @Column
    private Boolean attendance;

    @Column
    @CreationTimestamp
    private Date dateCreated;

    @Column
    @UpdateTimestamp
    private Date lastUpdated;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;


    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "reservation_seat",
            joinColumns = @JoinColumn(name = "reservation_id"),
            inverseJoinColumns = @JoinColumn(name = "seat_id")
    )
    private List<Seat> seats;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "seance_id",nullable = false)
    private Seance seance;
}

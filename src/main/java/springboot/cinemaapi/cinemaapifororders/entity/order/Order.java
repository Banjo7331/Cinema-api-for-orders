package springboot.cinemaapi.cinemaapifororders.entity.order;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private String couponCode;

    @Column(nullable = false)
    private Boolean isReservationForMovie;

    @Column(nullable = false)
    private BigDecimal totalPrice;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Product> orderItems = new ArrayList<>();
}

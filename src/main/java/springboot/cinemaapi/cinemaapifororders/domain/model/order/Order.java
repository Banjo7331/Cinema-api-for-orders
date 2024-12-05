package springboot.cinemaapi.cinemaapifororders.domain.model.order;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "CHAR(36)", unique = true, nullable = false)
    private String id;

    @Pattern(regexp = "^[A-Z]{6}$", message = "The code must consist of exactly 6 uppercase letters")
    @Column
    private String couponCode;

    @NotNull
    @Column
    private Boolean isReservationForMovie;

    @NotNull
    @Column
    private BigDecimal totalPrice;

    @Size(min = 1, max = 50)
    @OneToMany(fetch = FetchType.LAZY)
    private List<Product> orderItems = new ArrayList<>();
}

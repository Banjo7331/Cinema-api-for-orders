package springboot.cinemaapi.cinemaapifororders.domain.model.order;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import springboot.cinemaapi.cinemaapifororders.domain.enums.ProductType;

import java.math.BigDecimal;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products",uniqueConstraints = {@UniqueConstraint(columnNames = {"code"})})
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "CHAR(36)", unique = true, nullable = false)
    private String id;

    @NotNull
    @Size(min = 3, max = 15)
    @Column
    private String name;

    @NotNull
    @Pattern(regexp = "^[A-Z]{6}$", message = "Code must contain exactly 6 uppercase letters")
    @Column
    private String code;

    @NotNull
    @DecimalMin(value = "0.99")
    @Column
    private BigDecimal price;

    @NotNull
    @Column(name = "product_type")
    private ProductType productType;


}

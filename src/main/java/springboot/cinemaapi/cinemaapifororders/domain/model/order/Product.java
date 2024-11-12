package springboot.cinemaapi.cinemaapifororders.domain.model.order;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import springboot.cinemaapi.cinemaapifororders.domain.enums.ProductType;

import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products",uniqueConstraints = {@UniqueConstraint(columnNames = {"code"})})
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

package springboot.cinemaapi.cinemaapifororders.application.dto.order;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
public class ProductDto {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    @Pattern(regexp = "^[A-Z]{6}$", message = "Code must contain exactly 6 uppercase letters")
    private String code;

    @NotNull
    private BigDecimal price;

    @NotNull
    private ProductType productType;
}

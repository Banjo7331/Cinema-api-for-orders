package springboot.cinemaapi.cinemaapifororders.application.dto.order;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

    private Long id;

    private List<ProductDto> orderItems;

    @Pattern(regexp = "^[A-Z]{6}$", message = "The code must consist of exactly 6 uppercase letters")
    private String couponCode;

    private Boolean  isReservationForMovie;

    @NotNull
    private BigDecimal totalPrice;


}

package springboot.cinemaapi.cinemaapifororders.payload.dto.order;

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

    private String couponCode;

    private Boolean  isReservationForMovie;

    private BigDecimal totalPrice;


}

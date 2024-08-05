package springboot.cinemaapi.cinemaapifororders.payload.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import springboot.cinemaapi.cinemaapifororders.entity.order.Product;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

    private Long id;

    private List<Product> orderItems;

    private String couponCode;

    private boolean isReservationForMovie;

    private BigDecimal totalPrice;


}

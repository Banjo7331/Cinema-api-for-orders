package springboot.cinemaapi.cinemaapifororders.application.dto.order;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {

    private String id;

    private Boolean  isReservationForMovie;

    private String orderScanCode;

    private BigDecimal totalPrice;

    private List<ProductResponse> products;

}

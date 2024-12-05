package springboot.cinemaapi.cinemaapifororders.application.dto.order;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

    @Pattern(regexp = "^[A-Z]{6}$", message = "The code must consist of exactly 6 uppercase letters")
    private String couponCode;

    private Boolean  isReservationForMovie;

    @Size(min = 1, message = "numberOfProducts must be at least 1")
    private List<String> productsIds;

}

package springboot.cinemaapi.cinemaapifororders.application.ports.input.order;

import org.springframework.data.domain.Page;
import springboot.cinemaapi.cinemaapifororders.application.dto.order.ProductRequest;
import springboot.cinemaapi.cinemaapifororders.application.dto.order.ProductResponse;
import springboot.cinemaapi.cinemaapifororders.domain.enums.ProductType;

import java.util.UUID;

public interface ProductService {
    ProductResponse findProductById(String id);
    Page<ProductResponse> findProducts(ProductType type, Integer page, Integer size);
    ProductResponse addProduct(ProductRequest productDto);
    ProductResponse updateProduct(String id, ProductRequest productDto);
}

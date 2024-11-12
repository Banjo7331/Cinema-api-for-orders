package springboot.cinemaapi.cinemaapifororders.application.ports.input.order;

import org.springframework.data.domain.Page;
import springboot.cinemaapi.cinemaapifororders.application.dto.order.ProductDto;
import springboot.cinemaapi.cinemaapifororders.domain.enums.ProductType;

public interface ProductService {
    ProductDto findProductById(Long id);
    Page<ProductDto> findProducts(ProductType type, Integer page, Integer size);
    ProductDto addProduct(ProductDto productDto);
    ProductDto updateProduct(Long id,ProductDto productDto);
}

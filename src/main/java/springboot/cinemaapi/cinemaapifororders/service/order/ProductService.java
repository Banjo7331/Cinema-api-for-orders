package springboot.cinemaapi.cinemaapifororders.service.order;

import org.springframework.data.domain.Page;
import springboot.cinemaapi.cinemaapifororders.entity.order.Product;
import springboot.cinemaapi.cinemaapifororders.payload.dto.order.ProductDto;
import springboot.cinemaapi.cinemaapifororders.payload.enums.ProductType;

import java.util.List;

public interface ProductService {
    ProductDto findProductById(Long id);
    Page<ProductDto> findProducts(ProductType type, Integer page, Integer size);
    ProductDto addProduct(ProductDto productDto);
    ProductDto updateProduct(Long id,ProductDto productDto);
}

package springboot.cinemaapi.cinemaapifororders.service.order;

import springboot.cinemaapi.cinemaapifororders.entity.order.Product;
import springboot.cinemaapi.cinemaapifororders.payload.dto.order.ProductDto;
import springboot.cinemaapi.cinemaapifororders.payload.enums.ProductType;

import java.util.List;

public interface ProductService {
    ProductDto findProductById(Long id);
    List<ProductDto> getProducts(ProductType type);
    ProductDto addProduct(ProductDto productDto);
    ProductDto updateProduct(Long id,ProductDto productDto);
}

package springboot.cinemaapi.cinemaapifororders.service.order;

import springboot.cinemaapi.cinemaapifororders.entity.order.Product;
import springboot.cinemaapi.cinemaapifororders.payload.dto.order.ProductDto;

import java.util.List;

public interface ProductService {
    ProductDto findProductById(Long id);
    List<ProductDto> getProducts(Product.Type type);
    ProductDto addProduct(ProductDto productDto);
    ProductDto updateProduct(Long id,ProductDto productDto);
}

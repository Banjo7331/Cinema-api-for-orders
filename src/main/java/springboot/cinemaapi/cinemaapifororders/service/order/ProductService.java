package springboot.cinemaapi.cinemaapifororders.service.order;

import springboot.cinemaapi.cinemaapifororders.entity.order.Product;

import java.util.List;

public interface ProductService {
    List<Product> getProducts(Product.Type type);
}

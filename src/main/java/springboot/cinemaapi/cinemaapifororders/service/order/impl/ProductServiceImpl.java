package springboot.cinemaapi.cinemaapifororders.service.order.impl;

import org.springframework.stereotype.Service;
import springboot.cinemaapi.cinemaapifororders.entity.order.Product;
import springboot.cinemaapi.cinemaapifororders.repository.ProductRepository;
import springboot.cinemaapi.cinemaapifororders.service.order.ProductService;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getProducts(Product.Type type) {
        if(type == null){
            return productRepository.findAll();
        }

        return productRepository.findAllProductByType(type);

    }
}

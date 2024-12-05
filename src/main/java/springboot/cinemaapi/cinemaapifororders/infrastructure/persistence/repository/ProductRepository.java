package springboot.cinemaapi.cinemaapifororders.infrastructure.persistence.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import springboot.cinemaapi.cinemaapifororders.domain.model.order.Product;
import springboot.cinemaapi.cinemaapifororders.domain.enums.ProductType;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, String> {

    Page<Product> findAllProductByProductType(ProductType productType, Pageable pageable);
}

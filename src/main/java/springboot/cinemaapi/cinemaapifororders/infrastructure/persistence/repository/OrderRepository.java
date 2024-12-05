package springboot.cinemaapi.cinemaapifororders.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springboot.cinemaapi.cinemaapifororders.domain.model.order.Order;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, String> {
}

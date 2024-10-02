package springboot.cinemaapi.cinemaapifororders.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springboot.cinemaapi.cinemaapifororders.entity.order.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}

package springboot.cinemaapi.cinemaapifororders.service.order;

import springboot.cinemaapi.cinemaapifororders.entity.order.Order;
import springboot.cinemaapi.cinemaapifororders.entity.order.Product;
import springboot.cinemaapi.cinemaapifororders.payload.dto.OrderDto;

import java.util.List;

public interface OrderService {
    Order createOrder(OrderDto order);
}

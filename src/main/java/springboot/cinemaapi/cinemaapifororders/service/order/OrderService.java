package springboot.cinemaapi.cinemaapifororders.service.order;

import springboot.cinemaapi.cinemaapifororders.entity.order.Order;
import springboot.cinemaapi.cinemaapifororders.payload.dto.order.OrderDto;

public interface OrderService {
    OrderDto createOrder(OrderDto order);
    OrderDto updateOrder(Long id, OrderDto orderDto);
    OrderDto getOrderById(Long orderId);
    void deleteOrderById(Long id);
}

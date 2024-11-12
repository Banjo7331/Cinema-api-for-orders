package springboot.cinemaapi.cinemaapifororders.application.ports.input.order;

import springboot.cinemaapi.cinemaapifororders.application.dto.order.OrderDto;

public interface OrderService {
    OrderDto addOrder(OrderDto order);
    OrderDto updateOrder(Long id, OrderDto orderDto);
    OrderDto findOrderById(Long orderId);
    void deleteOrderById(Long id);
}
